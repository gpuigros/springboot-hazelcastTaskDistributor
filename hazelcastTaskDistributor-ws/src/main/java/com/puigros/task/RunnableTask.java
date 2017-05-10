package com.puigros.task;

import java.io.Serializable;
import java.util.Date;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class RunnableTask implements Runnable, Serializable, HazelcastInstanceAware, ApplicationContextAware {
    private int taskId;
    private transient HazelcastInstance haz;
    private transient ApplicationContext appCtx;

    public RunnableTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        log.info("Ejecutado " + taskId + "(" + new Date() + ") on haz=" + haz + " and appCtx=" + appCtx);

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
