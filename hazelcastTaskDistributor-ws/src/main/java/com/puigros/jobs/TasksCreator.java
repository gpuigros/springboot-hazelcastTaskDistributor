package com.puigros.jobs;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import com.puigros.logging.MDCInjector;
import com.puigros.task.RunnableTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
            IQueue<RunnableTask> queue = haz.getQueue( "queue" );
            RunnableTask runnableTask = context.getBean(RunnableTask.class, i);
            try {
                queue.put(runnableTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
