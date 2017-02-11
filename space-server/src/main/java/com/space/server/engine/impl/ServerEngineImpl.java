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
import java.util.concurrent.*;

import static com.space.server.engine.api.WorldEventType.UPDATE;

/**
 * Start and Stops games. Steps running games ever second and sends result to client
 * Created by superernie77 on 02.02.2017.
 */
@Component
public class ServerEngineImpl implements ServerEngine{

    private static final Logger LOG = LoggerFactory.getLogger(ServerEngineImpl.class);

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private GameEngine engine;

    private Map<Integer, Set<Integer>> playerWorldMap = new ConcurrentHashMap<>();

    private Map<Integer, Session> playerSessionMap = new ConcurrentHashMap<>();

    private Map<Integer, ScheduledFuture> worldFutureMap = new ConcurrentHashMap<>();

    private boolean checkGameStartedAlready(int worldId, int playerId){
        Set<Integer> players = playerWorldMap.get(worldId);
        return players != null && players.contains(playerId);
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

                // create result for client
                WorldEvent resultEvent = b.createWorldEvent();

                // broadcast to all players
                Set<Integer> playerSetRunnable = playerWorldMap.get(b.getWorldId());
                try {
                    for (Integer playerIdRunnable : playerSetRunnable ) {
                        LOG.debug("Broadcasting world for playerId "+playerIdRunnable);
                        Session playerSession = playerSessionMap.get(playerIdRunnable);
                        b.broadcast(playerSession,resultEvent);
                        LOG.debug(JsonUtil.toJson(resultEvent));
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(),e);
                }
            };

            ScheduledFuture future = scheduledExecutorService.scheduleAtFixedRate(r , 3, 1000L, TimeUnit.MILLISECONDS);

            // register world, player, future and session
            Set<Integer> newPlayerSet = new HashSet<>();
            newPlayerSet.add(playerId);
            playerWorldMap.put(worldId, newPlayerSet );
            playerSessionMap.put(playerId,session);
            worldFutureMap.put(worldId, future);
        }
     }

    @Override
    public  void addEvent(WorldEvent event ){
        SpaceWorld world = engine.getWorld(event.getWorldId());
        world.addEvent(event);
    }

    /**
     * Stops a game and ends the broadcasting of the game world
     * @param event stop event
     */
    @Override
    public  void stopGame(WorldEvent event){
        Integer worldId = event.getWorldId();
        Integer playerId = event.getPlayerId();

        // stop game for player
        engine.stopGame(playerId,worldId);

        // stop broadcasting for player
        if (playerWorldMap.get(worldId) != null) {
            playerWorldMap.get(worldId).remove(event.getPlayerId());
            if (playerWorldMap.get(worldId).size() == 0){
                playerWorldMap.remove(worldId);
                ScheduledFuture future = worldFutureMap.get(worldId);
                future.cancel(true);
                worldFutureMap.remove(worldId);
            }
            playerSessionMap.remove(event.getPlayerId());
        }
    }

    @Override
    public void shutdownDatabase(){
        engine.shutdownDatabase();
    }
}
