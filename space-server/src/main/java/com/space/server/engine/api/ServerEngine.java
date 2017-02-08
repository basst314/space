package com.space.server.engine.api;

import org.eclipse.jetty.websocket.api.Session;

/**
 * Interface for main server engine.
 * Created by superernie77 on 01.12.2016.
 */
public interface ServerEngine {

    void startGame(int worldId,int player, Session session);

    void addEvent(WorldEvent event );

    void stopGame(WorldEvent event);

    /**
     * Shuts down the space database
     */
    public void shutdownDatabase();

}
