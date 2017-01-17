package com.space.server.web.util;

import com.space.server.engine.api.GameEngine;
import com.space.server.engine.impl.GameEngineImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Starts the spring application
 * Created by superernie77 on 17.01.2017.
 */
@Configuration
@ComponentScan({ "com.space" })
public class SpringStarter {

    public GameEngine startSpringContext(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringStarter.class);
        ctx.registerShutdownHook();
        return ctx.getBean(GameEngineImpl.class);
    }
}
