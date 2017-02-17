package com.space.server.web.util;

import com.space.server.engine.impl.GameEngineImpl;
import com.space.server.engine.impl.ServerEngineImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Starts the spring application
 * Created by superernie77 on 17.01.2017.
 */
@Configuration
@ComponentScan({"com.space"})
public class SpringStarter {

    private static final int MAX_THREADS = 5;

	public ServerEngineImpl startSpringContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringStarter.class);
		ctx.registerShutdownHook();
		return ctx.getBean(ServerEngineImpl.class);
	}

	public GameEngineImpl startSpringGameEngine(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringStarter.class);
        ctx.registerShutdownHook();
        return ctx.getBean(GameEngineImpl.class);
    }

  @Bean
  public EmbeddedDatabase dataSource() {
      EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
      EmbeddedDatabase db = builder
              .setType(EmbeddedDatabaseType.HSQL)
              .addScript("sql/create-db.sql")
              .addScript("sql/insert-data.sql")
              .build();
      return db;
  }

  @Bean
    public ScheduledExecutorService createExecutorService(){
      return Executors.newScheduledThreadPool(MAX_THREADS);
  }

}
