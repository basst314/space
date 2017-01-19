package com.space.server.web.util;

import com.space.server.engine.api.GameEngine;
import com.space.server.engine.impl.GameEngineImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Starts the spring application
 * Created by superernie77 on 17.01.2017.
 */
@Configuration
@ComponentScan({"com.space"})
public class SpringStarter {

	public GameEngine startSpringContext() {
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

}
