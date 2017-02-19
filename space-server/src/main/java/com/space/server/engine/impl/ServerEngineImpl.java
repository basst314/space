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
 * Start and Stops games. Steps running games every second and sends result to client(s)
 * Created by superernie77 on 02.02.2017.
 */
@Component
public class ServerEngineImpl implements ServerEngine{

    private static final Logger LOG = LoggerFactory.getLogger(ServerEngineImpl.class);

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private GameEngine engine;

    // Maps a Set of player ids to a worldId
    private Map<Integer, Set<Integer>> playerWorldMap = new ConcurrentHashMap<>();

    // Maps a player id to a websocket session
    private Map<Integer, Session> playerSessionMap = new ConcurrentHashMap<>();

    // Maps a world id to its broadcasting thread
    private Map<Integer, ScheduledFuture> worldFutureMap = new ConcurrentHashMap<>();

    private boolean checkGameStartedAlready(int worldId, int playerId){
        Set<Integer> players = playerWorldMap.get(worldId);
        return players != null && players.contains(playerId);
    }

    class Runner implements  Runnable {

        private Broadcaster b;

        public void setBroadCaster(Broadcaster broad) { b = broad; };

        public void run(){
            LOG.debug("Steping world ....");
            // step world one step
            b.getEngine().stepWorld(b.getWorldId());

            // broadcast to all players
            Set<Integer> playerSetRunnable = playerWorldMap.get(b.getWorldId());
            if (playerSetRunnable != null) {
                try {
                    LOG.debug("Broadcasting for {} players", playerSetRunnable.size());
                    for (Integer playerIdRunnable : playerSetRunnable ) {
                        LOG.debug("Broadcasting world for playerId "+playerIdRunnable);
                        WorldEvent resultEvent = b.createWorldEvent(playerIdRunnable);
                        Session playerSession = playerSessionMap.get(playerIdRunnable);
                        LOG.debug("Player {] session {}", playerIdRunnable, playerSession.toString());
                        b.broadcast(playerSession,resultEvent);
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void startGame(int worldId,int playerId, Session session) {

        if (checkGameStartedAlready(worldId,playerId)){
            LOG.debug("World {} already started for player {}", worldId, playerId);
            return;
        }

        Set<Integer> players = playerWorldMap.get(worldId);
        if (players != null) {
            LOG.debug("World {} running already.",worldId);

            engine.startGame(playerId,worldId);

            LOG.debug("Adding player {} to playerWorldMap",playerId);
            players.add(playerId);

            LOG.debug("Adding session for player {}", playerId);
            playerSessionMap.put(playerId,session);


        } else {
            // start new world

            engine.startGame(playerId,worldId);

            Broadcaster b = new Broadcaster();
            b.setWorldId(worldId);
            b.setEngine(engine);

            Runner runner = new Runner();
            runner.setBroadCaster(b);


            LOG.debug("Starting new Runner for worldId {}",worldId );
            ScheduledFuture future = scheduledExecutorService.scheduleAtFixedRate(runner , 3, 1000L, TimeUnit.MILLISECONDS);

            // register world, player, future and session
            Set<Integer> newPlayerSet = new HashSet<>();
            newPlayerSet.add(playerId);

            LOG.debug("Adding player {} to world",playerId);
            playerWorldMap.put(worldId, newPlayerSet );

            LOG.debug("Adding session for player {}", playerId);
            playerSessionMap.put(playerId,session);

            LOG.debug("Adding future for world {}", worldId);
            worldFutureMap.put(worldId, future);
        }
     }

    @Override
    public void addEvent(WorldEvent event ){
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

    public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    public Map<Integer, Set<Integer>> getPlayerWorldMap() {
        return playerWorldMap;
    }

    public Map<Integer, Session> getPlayerSessionMap() {
        return playerSessionMap;
    }

    public Map<Integer, ScheduledFuture> getWorldFutureMap() {
        return worldFutureMap;
    }

}
