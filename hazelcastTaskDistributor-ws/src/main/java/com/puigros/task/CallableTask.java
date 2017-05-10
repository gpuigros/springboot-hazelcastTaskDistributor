package com.puigros.task;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.spring.context.SpringAware;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.Callable;

@SpringAware
@Component
@Scope("prototype")
@Log4j2
public class CallableTask implements Callable<Result>, Serializable, HazelcastInstanceAware, ApplicationContextAware {
    private int taskId;
    private transient HazelcastInstance haz;
    private transient ApplicationContext appCtx;

    public CallableTask(int taskId) {
        this.taskId = taskId;
    }



    @Override
    public void setHazelcastInstance(HazelcastInstance haz) {
        this.haz = haz;
    }

    @Override
    public void setApplicationContext(ApplicationContext appCtx) throws BeansException {
        this.appCtx = appCtx;
    }

    @Override public Result call() throws Exception {
        String msg="Ejecutado " + taskId + "(" + new Date() + ") on haz=" + haz + " and appCtx=" + appCtx;
        log.info(msg);
        Result ret = new Result();
        ret.setStatus("OK");
        ret.setMessage(msg);
        return ret;
    }
}
