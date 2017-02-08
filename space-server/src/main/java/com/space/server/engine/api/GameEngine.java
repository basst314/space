package com.space.server.engine.api;

import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;

public interface GameEngine {

    /**
     * Start a space game for a given player and world.
     * @param playerId
     * @param worldId
     */
    void startGame(Integer playerId, Integer worldId);

    /**
     * Stops a space game for a given player and world.
     * @param playerId
     * @param worldId
     */
    void stopGame(Integer playerId, Integer worldId);

    /**
     * Advances the world by one time step
     * @param worldId
     */
    void stepWorld(Integer worldId);

    /**
     * Adds an event to a world
     * @param event
     */
    void addEvent(WorldEvent event);

    /**
     * Returns the world a player plays in
     * @param playerId
     * @return
     */
    SpaceWorld getWorld(Integer playerId);

    /**
     * saves the state of a world
     * @param worldId
     */
    void persist(Integer worldId);

    /**
     * return a player
     * @param playerId
     * @return
     */
    SpacePlayer getPlayer(Integer playerId);

    public void shutdownDatabase();
}
