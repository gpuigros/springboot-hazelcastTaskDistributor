package com.puigros.jobs;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IQueue;
import com.puigros.logging.MDCInjector;
import com.puigros.task.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Created by guillem.puigros on 10/05/2017.
 */
@Log4j2
@Component
@Scope("prototype")
public class TasksCreator implements Runnable {
    @Autowired
    private MDCInjector mDCInjector;
    @Autowired
    ApplicationContext context;

    @Autowired
    private HazelcastInstance haz;

    private int tasks;

    public TasksCreator(int tasks){
        this.tasks=tasks;
    }
    @Override
    public void run() {
        mDCInjector.setMDC();
        for (int i=0;i<tasks;i++){
            log.info("Set task "+i);
            IQueue<Task> queue = haz.getQueue( "queue" );
            Task task = context.getBean(Task.class, i);
            try {
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
