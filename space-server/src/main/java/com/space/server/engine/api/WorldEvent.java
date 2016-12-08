package com.space.server.engine.api;

/**
 * interface for worldEvent to transport event information into the game world.
 * Created by superernie77 on 01.12.2016.
 */
public interface WorldEvent {

    void setWorldId(int worldId);

    int getWorldId();

    void setPlayerId(int playerId);

    int getPlayerId();

    WorldEventType getType();

    void setType(WorldEventType type);



}
