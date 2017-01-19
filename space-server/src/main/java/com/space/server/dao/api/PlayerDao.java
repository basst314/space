package com.space.server.dao.api;

import com.space.server.domain.api.SpacePlayer;

/**
 * Database access to player information
 * Created by superernie77 on 01.12.2016.
 */
public interface PlayerDao {

    /**
     * Load a player with given ID from the database
     * @param playerId playerId to load
     * @return loaded player
     */
    SpacePlayer getPlayer(int playerId);


    void savePlayer(SpacePlayer player);

}