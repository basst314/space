package com.space.server.engine.impl;

import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;

/**
 * Action of a player in a sppace world.
 * Created by superernie77 on 08.12.2016.
 */
public class WorldEventImpl implements WorldEvent {

    private Integer worldId;

    private Integer playerId;

    private WorldEventType worldEventType;

    @Override
    public void setWorldId(int worldId) {
        this.worldId = worldId ;
    }

    @Override
    public int getWorldId() {
        return worldId;
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public WorldEventType getType() {
        return null;
    }

    @Override
    public void setType(WorldEventType type) {
        worldEventType = type;
    }
}
