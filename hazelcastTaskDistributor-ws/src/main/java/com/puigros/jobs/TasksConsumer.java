package com.puigros.jobs;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import com.puigros.logging.MDCInjector;
import com.puigros.task.RunnableTask;
import lombok.Data;
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
@Data
public class TasksConsumer implements Runnable {
    @Autowired
    private MDCInjector mDCInjector;
    @Autowired
    ApplicationContext context;

    @Autowired
    private HazelcastInstance haz;


    private boolean end;


    @Override
    public void run() {
        mDCInjector.setMDC();
        end=false;
        while (!end) {
            IQueue<RunnableTask> queue = haz.getQueue("queue");
            RunnableTask runnableTask = null;
            try {
                runnableTask = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runnableTask.run();
        }
    }
}
