package com.puigros.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by guillem.puigros on 10/05/2017.
 */
@Configuration
@Log4j2
public class ThreadConfig {

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor
                = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("Processors:"+processors);
        threadPoolTaskExecutor.setCorePoolSize(processors * 2); //Initial pool
        // When application start receiving message requests for each request we create a new thread up to the core poll size
        threadPoolTaskExecutor.setMaxPoolSize(processors*5); //Max
        //After core and capacity is full, if we have no available core threads and no space in the queue it will spawn new thread up to the limit of the max pool size.
        threadPoolTaskExecutor.setQueueCapacity(25);// BlockingQueue
        //The applciation will add unprocessed message on the queue until it reach it capacity of 25
        threadPoolTaskExecutor.initialize();

        return new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);
    }
}
