package com.space.server.engine.impl;

import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.api.Step;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.utils.StepUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Implementation of the GameEngine. Starts new games and stops running games.
 * Manages active players and worlds. Can be used to persist a game world.
 * Created by superernie77 on 08.12.2016.
 */
@Service
public class GameEngineImpl implements GameEngine {

    private static final Logger LOG = LoggerFactory.getLogger(GameEngineImpl.class);

    @Autowired
    private StepUtils stepUtils;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private WorldDao worldDao;

    @Autowired
    private EmbeddedDatabase db;

    @Autowired
    private WorldEventProcessorImpl processor;

    private Map<Integer,SpacePlayer> activePlayer = new HashMap<>();

    private Map<Integer,SpaceWorld> activeWorlds = new HashMap<>();

    private Map<Integer,Integer> playerWorldmapping = new HashMap<>();

    @Override
    public void startGame(Integer playerId, Integer worldId) {
        LOG.info("Starting game for playerId {} and worldId {}",playerId, worldId);

        // load player
        SpacePlayer player = playerDao.getPlayer(playerId);
        activePlayer.put(playerId,player);

        // load world
        SpaceWorld world = worldDao.getWorld(worldId);

        // set player into world and connect player with step
        Segment segment = world.getSegment(world.getStartSegment());
        Step step = segment.getStep(world.getStartStep());
        step.addOverlay(player);
        player.setActiveStep(step);

        // activate world
        activeWorlds.put(worldId,world);

        // map player to world
        playerWorldmapping.put(playerId,worldId);

        LOG.info("Game started for playerId {} and worldId {}",playerId, worldId);
    }

    @Override
    public void stopGame(Integer playerId, Integer worldId) {
        LOG.info("Stoping game for playerId {} and worldId {}",playerId, worldId);

        activePlayer.remove(playerId);
        activeWorlds.remove(worldId);
        playerWorldmapping.remove(playerId);

        LOG.info("Game stopped for playerId {} and worldId {}",playerId, worldId);
    }

    @Override
    public void stepWorld(Integer worldId) {
        LOG.debug("Stepping for worldId {}", worldId);

        SpaceWorld world = activeWorlds.get(worldId);
        // process events
        for (SpacePlayer player : activePlayer.values()){
            // only process if player is actively playing in this world
            if (playerWorldmapping.containsKey(player.getPlayerId())) {
                // reset player state for this step
                player.resetActivities();

                // process all player events
                List<WorldEvent> events = world.getEventsForPlayer(player.getPlayerId());
                processor.processEvents(events,player);
                world.removeEvents(events);
            }
        }

        // monster hit players
        world.getSegment(0).getAllSteps().stream()
                .filter( s ->  s.isPlayerPresent())
                .map( s -> s.getPlayers() )
                .flatMap( p -> p.stream() )
                .forEach(p -> stepUtils.monsterCombat(p.getActiveStep(), p));

        // remove dead players
        world.getSegment(0).getAllSteps().stream()
                .filter( s ->  s.isPlayerPresent())
                .map( s -> s.getPlayers() )
                .flatMap( p -> p.stream() )
                .forEach(p ->  { if (p.getHealth().isDead()) p.getActiveStep().getOverlays().remove(p); } );

        // move players
        world.getSegment(0).getAllSteps().stream()
                .filter( s ->  s.isPlayerPresent())
                .map( s -> s.getPlayers() )
                .flatMap( p -> p.stream() )
                .forEach(p -> stepUtils.movePlayerOneStep(p.getActiveStep()));

        LOG.debug(" worldId {} has been stepped.", worldId);
    }

    @Override
    public void addEvent(WorldEvent event) {
        LOG.debug("New event {} for worldId {} and playerId {} added",event.getType().toString(), event.getWorldId(), event.getPlayerId());
        SpaceWorld world = activeWorlds.get(event.getWorldId());
        if (world != null) {
            world.addEvent(event);
        }
    }

    @Override
    public SpaceWorld getWorld(Integer worldId) {
        SpaceWorld world = activeWorlds.get(worldId);
        if (world == null){
            world = worldDao.getWorld(worldId);
            LOG.debug("World (worldId {}) has been loaded.", world.getWorldId());
        }
        return world;
    }

    @Override
    public void persist(Integer worldId) {
        SpaceWorld world = activeWorlds.get(worldId);
        if (world != null) {
            worldDao.saveWorld(world);
            LOG.debug("World (worldId {}) has been persisted.", worldId);
        }
    }

    @Override
    public SpacePlayer getPlayer(Integer playerId) {
        SpacePlayer player = activePlayer.get(playerId);
        if (player == null) {
            playerDao.getPlayer(playerId);
            if (player != null) {
                LOG.debug("Player (playerId {}) has been loaded.", player.getPlayerId());
            }
        }
        return player;
    }

    @Override
    public void shutdownDatabase(){
        db.shutdown();
    }

    void setWorldDao(WorldDao dao){
        worldDao = dao;
    }

    void setPlayerDao(PlayerDao dao){
        playerDao = dao;
    }

    public WorldEventProcessorImpl getWorldEventProcessor(){
        return processor;
    }

    void setWorldEventProcessor(WorldEventProcessorImpl proc) {
        processor = proc;
    }
}