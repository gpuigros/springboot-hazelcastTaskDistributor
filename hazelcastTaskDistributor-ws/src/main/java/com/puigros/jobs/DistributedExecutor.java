package com.puigros.jobs;

import com.hazelcast.core.HazelcastInstance;
import com.puigros.logging.MDCInjector;
import com.puigros.task.CallableTask;
import com.puigros.task.Result;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by guillem.puigros on 10/05/2017.
 */
@Log4j2
@Component
@Scope("prototype")
@Data
public class DistributedExecutor implements Runnable {
    @Autowired
    private MDCInjector mDCInjector;
    @Autowired
    ApplicationContext context;

    @Autowired
    private HazelcastInstance haz;


    private boolean end;
    private int tasks;


    public DistributedExecutor(int tasks){
        this.tasks=tasks;
    }



    @Override
    public void run() {
        mDCInjector.setMDC();
        List<Future<Result>> results=new ArrayList<Future<Result>>();
        ExecutorService executorService = haz.getExecutorService("exec");
        for (int i=0;i<tasks;i++){
            CallableTask task = context.getBean(CallableTask.class, i);
            Future<Result> future = executorService.submit( task );
            //while it is executing, do some useful stuff
            //when ready, get the result of your execution
            results.add(future);

        }

        for (Future<Result> fut:results){
            Result result = null;
            try {
                result = fut.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            log.info(" Status:"+result.getStatus()+" Message:"+result.getMessage());
        }
    }
}
