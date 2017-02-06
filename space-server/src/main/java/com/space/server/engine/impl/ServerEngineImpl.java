package com.space.server.engine.impl;

import com.space.server.core.World;
import com.space.server.domain.api.SpacePlayer;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

    private Map<Integer, Set<Integer>> playerWorldMap = new ConcurrentHashMap<>();

    private Map<Integer, Session> playerSessionMap = new ConcurrentHashMap<>();

    private boolean checkGameStartedAlready(int worldId, int playerId){
        Set<Integer> players = playerWorldMap.get(worldId);
        if (players != null){
            return players.contains(playerId);
        }
        return false;
    }

    @Override
    public void startGame(int worldId,int playerId, Session session) {

        if (checkGameStartedAlready(worldId,playerId)){
            return;
        }

        Set<Integer> players = playerWorldMap.get(worldId);
        if (players != null) {
            // world started already

            // add player to world
            players.add(playerId);

            // save session
            playerSessionMap.put(playerId,session);
        } else {
            // start new world

            engine.startGame(playerId,worldId);

            Broadcaster b = new Broadcaster();
            b.setWorldId(worldId);
            b.setEngine(engine);

            Runnable r = () -> {

                LOG.debug("Steping world ....");
                // step world one step
                b.getEngine().stepWorld(b.getWorldId());
                SpaceWorld world = engine.getWorld(b.getWorldId());
                World gameWorld = new World(world.getSegment(0).getContent());
                WorldEvent resultEvent = new WorldEventImpl();
                resultEvent.setPlayerId(b.getPlayerId());
                resultEvent.setWorldId(b.getWorldId());
                resultEvent.setType(UPDATE);
                resultEvent.setWorld(gameWorld);

                // broadcast to all players
                LOG.debug("Broadcasting world ....");
                Set<Integer> playerSetRunnable = playerWorldMap.get(b.getWorldId());
                try {
                    for (Integer playerIdRunnable : playerSetRunnable ) {
                        Session playerSession = playerSessionMap.get(playerIdRunnable);
                        playerSession.getRemote().sendString(JsonUtil.toJson(resultEvent));
                        System.out.println(JsonUtil.toJson(resultEvent));
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(),e);
                }
            };

            scheduledExecutorService.scheduleAtFixedRate(r , 3, 1000L, TimeUnit.MILLISECONDS);

            // register world, player and session
            Set<Integer> newPlayerSet = new HashSet<>();
            newPlayerSet.add(playerId);
            playerWorldMap.put(worldId, newPlayerSet );
            playerSessionMap.put(playerId,session);
        }
     }

    @Override
    public  void addEvent(WorldEvent event ){
        SpaceWorld world = engine.getWorld(event.getWorldId());
        world.addEvent(event);
    }

    @Override
    public  void stopGame(WorldEvent event){
        engine.stopGame(event.getPlayerId(), event.getWorldId());

        playerWorldMap.get(event.getWorldId()).remove(event.getPlayerId());

        playerSessionMap.remove(event.getPlayerId());

        //TODO stop thread for game
    }

    @Override
    public void shutdownDatabase(){
        engine.shutdownDatabase();
    }
}
