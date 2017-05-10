package com.puigros.service.impl;

import com.puigros.jobs.DistributedExecutor;
import com.puigros.jobs.TasksConsumer;
import com.puigros.jobs.TasksCreator;
import com.puigros.service.TaskDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service("TaskDistributorService")

public class TaskDistributorServiceImpl implements TaskDistributorService {
    @Autowired
    private Executor executor;
    @Autowired
    ApplicationContext context;

    @Override
    public void runTasksCreation(int tasks){
        executor.execute(context.getBean(TasksCreator.class, tasks));
    }
    @Override
    public void runTasksConsumer(){
        executor.execute(context.getBean(TasksConsumer.class));
    }
    @Override
    public void runDistributedExecutor(int tasks){
        executor.execute(context.getBean(DistributedExecutor.class, tasks));
    }
}
