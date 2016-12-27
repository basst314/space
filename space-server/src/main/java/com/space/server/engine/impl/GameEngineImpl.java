package com.space.server.engine.impl;

import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.*;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.utils.StepUtils;

import java.util.*;

/**
 * Implementation of the GameEngine. Starts new games and stops running games.
 * Manages active players and worlds. Can be used to persist a game world.
 * Created by superernie77 on 08.12.2016.
 */
public class GameEngineImpl implements GameEngine {

    private StepUtils stepUtils = new StepUtils();

    private PlayerDao playerDao;

    private WorldDao worldDao;

    private WorldEventProcessor processor;

    private Map<Integer,SpacePlayer> activePlayer = new HashMap<>();

    private Map<Integer,SpaceWorld> activeWorlds = new HashMap<>();

    private Map<Integer,Integer> playerWorldmapping = new HashMap<>();

    @Override
    public void startGame(Integer playerId, Integer worldId) {
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
    }

    @Override
    public void stopGame(Integer playerId, Integer worldId) {
        activePlayer.remove(playerId);
        activeWorlds.remove(worldId);
        playerWorldmapping.remove(playerId);
    }

    @Override
    public void stepWorld(Integer worldId) {

        SpaceWorld world = activeWorlds.get(worldId);

        // process event
        for (SpacePlayer player : activePlayer.values()){
               List<WorldEvent>  events = world.getEventsForPlayer(player.getPlayerId());
                processor.processEvents(events,player);
        }

        // move players
        Step step = world.getSegment(0).getStep(0);
        if (step.isPlayerPresent()){
            stepUtils.movePlayerOneStep(step);
        } else {
            while (step.next() != null){
                step = step.next();
                if (step.isPlayerPresent()){
                    stepUtils.movePlayerOneStep(step);
                    break;
                }
            }
        }

        // reset moved flag after step is completed
        activePlayer.values().stream().forEach( p -> p.setMoved(false));

    }

    @Override
    public void addEvent(WorldEvent event) {
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
        }
        return world;
    }

    @Override
    public void persist(Integer worldId) {
        SpaceWorld world = activeWorlds.get(worldId);
        if (world != null){
            worldDao.saveWorld(world);
        }
    }

    @Override
    public SpacePlayer getPlayer(Integer playerId) {
        SpacePlayer player = activePlayer.get(playerId);
        if (player == null) {
            playerDao.getPlayer(playerId);
        }
        return player;
    }

    void setWorldDao(WorldDao dao){
        worldDao = dao;
    }

    void setPlayerDao(PlayerDao dao){
        playerDao = dao;
    }

    public void setWorldEventProcessor(WorldEventProcessor proc){
        processor = proc;
    }

    public WorldEventProcessor getWorldEventProcessor(){
        return processor;
    }
}