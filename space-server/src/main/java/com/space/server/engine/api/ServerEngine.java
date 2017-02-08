package com.space.server.engine.api;

import org.eclipse.jetty.websocket.api.Session;

/**
 * Interface for main server engine.
 * Created by superernie77 on 01.12.2016.
 */
public interface ServerEngine {

    /**
     * start a new game for a player
     * @param worldId world to start
     * @param player player to start
     * @param session websocket session
     */
    void startGame(int worldId,int player, Session session);

    /**
     * adds an event to a world
     * @param event
     */
    void addEvent(WorldEvent event );

    /**
     * stops a game for a player
     * @param event
     */
    void stopGame(WorldEvent event);

    /**
     * Shuts down the space database
     */
    public void shutdownDatabase();

}
