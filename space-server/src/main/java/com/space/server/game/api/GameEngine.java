package com.space.server.game.api;

import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.WorldEvent;

/**
 * Interface for space game manager. Stores and calculates the world state from the
 * active players in the world and any submited world events.
 * Created by superernie77 on 01.12.2016.
 */
public interface GameEngine {


    void startPlayer(int playerId, int worldId);

    void stoPlayer(int playerId, int worldId);

    void stepWorld(int worldId);

    void addEvent(WorldEvent event , int playerId);

    SpaceWorld getWorld(int playerId);

    void persist(int worldId);

    SpacePlayer getPlayer(int playerId);
}
