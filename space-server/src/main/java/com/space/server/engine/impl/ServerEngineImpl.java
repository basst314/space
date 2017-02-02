package com.space.server.engine.impl;

import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.ServerEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.web.util.JsonUtil;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.space.server.engine.api.WorldEventType.UPDATE;

/**
 * Start and Stops games. Steps running games ever second and sends result to client
 * Created by superernie77 on 02.02.2017.
 */
@Component
public class ServerEngineImpl implements ServerEngine{

    private static final Logger LOG = LoggerFactory.getLogger(ServerEngineImpl.class);

    @Autowired
    ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private GameEngine engine;

    @Override
    public void startGame(int worldId,int player, Session session) {

        Broadcaster b = new Broadcaster();
        b.setWorldId(worldId);
        b.setEngine(engine);
        b.setSession(session);

        Runnable r = () -> {
            b.getEngine().stepWorld(b.getWorldId());
            SpaceWorld world = engine.getWorld(b.getWorldId());
            World gameWorld = new World(world.getSegment(0).getContent());
            WorldEvent resultEvent = new WorldEventImpl();
            resultEvent.setPlayerId(b.getPlayerId());
            resultEvent.setWorldId(b.getWorldId());
            resultEvent.setType(UPDATE);
            resultEvent.setWorld(gameWorld);

            try {
                session.getRemote().sendString(JsonUtil.toJson(resultEvent));
            } catch (IOException e) {
                LOG.error(e.getMessage(),e);
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(r , 0, 1000L, TimeUnit.MILLISECONDS);
    }

    @Override
    public  void addEvent(WorldEvent event ){
        SpaceWorld world = engine.getWorld(event.getWorldId());
        world.addEvent(event);
    }

    @Override
    public  void stopGame(WorldEvent event){
        engine.stopGame(event.getPlayerId(), event.getWorldId());

        //TODO stop thread for game
    }

    @Override
    public void shutdownDatabase(){
        engine.shutdownDatabase();
    }


}
