package com.space.server.dao.api;

import com.space.server.domain.api.SpaceWorld;

import java.util.List;

/**
 * Service for access to Space-Worlds.
 * Created by superernie77 on 01.12.2016.
 */
public interface WorldDao {

    void saveWorld(SpaceWorld world);

    /**
     * Load a world with given ID from the database
     * @param worldId worldId to load
     * @return loaded world
     */
    SpaceWorld loadWorld(int worldId);

    List<SpaceWorld> getWorldsByPlayerId(int playerId);

    List<SpaceWorld> getWorlds();

    SpaceWorld getWorld(int worldId);
}
