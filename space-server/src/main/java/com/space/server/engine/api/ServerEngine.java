package com.space.server.engine.api;

/**
 * Interface for main server engine.
 * Created by superernie77 on 01.12.2016.
 */
public interface ServerEngine {

    void stop(int playerId, int worldId);

    void start(int playerId, int worldId);

    void addEvent(int playerId, int worldId, WorldEvent event );

}
