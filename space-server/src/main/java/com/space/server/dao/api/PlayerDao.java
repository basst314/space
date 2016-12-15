package com.space.server.dao.api;

import com.space.server.domain.api.SpacePlayer;

/**
 * Database access to player information
 * Created by superernie77 on 01.12.2016.
 */
public interface PlayerDao {

    SpacePlayer getPlayer(int playerId);

    void savePlayer(SpacePlayer player);

}