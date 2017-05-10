package com.puigros.task;

import java.io.Serializable;
import java.util.Date;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.spring.context.SpringAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SpringAware
@Component
@Scope("prototype")
public class Task implements Runnable, Serializable, HazelcastInstanceAware, ApplicationContextAware {
    private int taskId;
    private transient HazelcastInstance haz;
    private transient ApplicationContext appCtx;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Ejecutado " + taskId + "(" + new Date() + ") on haz=" + haz + " and appCtx=" + appCtx);

    }

    @Override
    public void setHazelcastInstance(HazelcastInstance haz) {
        this.haz = haz;
    }

    @Override
    public void setApplicationContext(ApplicationContext appCtx) throws BeansException {
        this.appCtx = appCtx;
    }
}
