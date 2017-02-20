package com.space.server.engine.impl;

import com.space.server.core.World;
import com.space.server.domain.api.Item;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Action of a player in a sppace world.
 * Created by superernie77 on 08.12.2016.
 */
@Service
public class WorldEventImpl implements WorldEvent {

    private Integer worldId;

    private Integer playerId;

    private WorldEventType worldEventType;

    private World world;

    private List<Item> items;

    @Override
    public int getWorldId() {
        return worldId;
    }

    @Override
    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public WorldEventType getType() {
        return worldEventType;
    }

    @Override
    public void setType(WorldEventType type) {
        worldEventType = type;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void setInventory(List<Item> items) {
        this.items = items;
    }

    @Override
    public List<Item> getInventory() {
        return items;
    }
}
