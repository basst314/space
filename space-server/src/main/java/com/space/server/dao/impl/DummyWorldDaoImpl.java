package com.space.server.dao.impl;

import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.utils.StepUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Dummy implementation of a WorldDAO. Always returns same world.
 * Created by superernie77 on 01.01.2017.
 */
public class DummyWorldDaoImpl implements WorldDao{

    private static final String dummyWorld = ".......W.......M...M";

    private StepUtils utils = new StepUtils();

    @Override
    public void saveWorld(SpaceWorld world) {

    }

    @Override
    public SpaceWorld loadWorld(int worldId) {
        return utils.createWorldFromString(dummyWorld);
    }

    @Override
    public List<SpaceWorld> getWorldsByPlayerId(int playerId) {
        return Arrays.asList(utils.createWorldFromString(dummyWorld));
    }

    @Override
    public List<SpaceWorld> getWorlds() {
        return Arrays.asList(utils.createWorldFromString(dummyWorld));
    }

    @Override
    public SpaceWorld getWorld(int worldId) {
        return utils.createWorldFromString(dummyWorld);
    }
}
