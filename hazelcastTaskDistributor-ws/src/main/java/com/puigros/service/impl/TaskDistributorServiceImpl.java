package com.puigros.service.impl;

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
        TasksCreator tasksClass=context.getBean(TasksCreator.class, tasks);
        executor.execute(tasksClass);
    }
    @Override
    public void runTasksConsumer(){
        TasksConsumer tasksClass=context.getBean(TasksConsumer.class);
        executor.execute(tasksClass);
    }
}
