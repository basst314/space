package com.space.server.engine.impl;

import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;

import java.util.*;

/**
 * Implementation of the GameEngine. Starts new games and stops running games.
 * Manages active players and worlds. Can be used to persist a game world.
 * Created by superernie77 on 08.12.2016.
 */
public class GameEngineImpl implements GameEngine {

    private PlayerDao playerDao;

    private WorldDao worldDao;

    private Map<Integer,SpacePlayer> activePlayer = new HashMap<>();

    private Map<Integer,SpaceWorld> activeWorlds = new HashMap<>();

    private Map<Integer,Integer> playerWorldmapping = new HashMap<>();

    @Override
    public void startPlayer(Integer playerId, Integer worldId) {
        SpacePlayer player = playerDao.getPlayer(playerId);
        activePlayer.put(player.getPlayerId(),player);


        SpaceWorld world = worldDao.getWorld(worldId);
        activeWorlds.put(world.getWorldId(),world);

        playerWorldmapping.put(playerId,worldId);
    }

    @Override
    public void stopPlayer(Integer playerId, Integer worldId) {
        activePlayer.remove(playerId);
        activeWorlds.remove(worldId);

        playerWorldmapping.remove(playerId);
    }

    @Override
    public void stepWorld(Integer worldId) {

    }

    @Override
    public void addEvent(WorldEvent event) {
        SpaceWorld world = activeWorlds.get(event.getWorldId());
        world.addEvent(event);
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
}