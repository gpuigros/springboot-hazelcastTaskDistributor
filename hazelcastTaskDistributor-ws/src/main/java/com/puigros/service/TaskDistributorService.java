package com.puigros.service;


public interface TaskDistributorService {


    void runTasksCreation(int tasks);

    void runTasksConsumer();

    void runDistributedExecutor(int tasks);
}
