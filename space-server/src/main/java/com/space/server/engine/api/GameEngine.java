package com.space.server.engine.api;

import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;

/**
 * Integererfaces for the GameEngine. The GameEngine processes all WorldEvents and
 * calculates the state of all players and worlds.
 * Created by superernie77 on 08.12.2016.
 */
public interface GameEngine {
    
    void startGame(Integer playerId, Integer worldId);

    void stopGame(Integer playerId, Integer worldId);
    
    void stepWorld(Integer worldId);
    
    void addEvent(WorldEvent event);
    
    SpaceWorld getWorld(Integer playerId);
    
    void persist(Integer worldId);
    
    SpacePlayer getPlayer(Integer playerId);
}
