package com.space.server.dao.impl;

import com.space.server.dao.api.PlayerDao;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.impl.SpacePlayerImpl;

/**
 * Created by superernie77 on 01.01.2017.
 */
public class DummyPlayerDaoImpl implements PlayerDao{

    static SpacePlayer thePlayer = new SpacePlayerImpl();

    static {
        thePlayer.setPlayerId(0);
    }

    @Override
    public SpacePlayer getPlayer(int playerId) {
        return thePlayer;
    }

    @Override
    public void savePlayer(SpacePlayer player) {

    }
}
