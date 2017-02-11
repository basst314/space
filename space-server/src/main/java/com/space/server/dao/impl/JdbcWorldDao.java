package com.space.server.dao.impl;

import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.SimpleWorldImpl;
import com.space.server.domain.impl.SpacePlayerImpl;
import com.space.server.utils.StepUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Dummy implementation of a WorldDAO. Always returns same world.
 * Created by superernie77 on 01.01.2017.
 */
@Repository
public class JdbcWorldDao implements WorldDao{

    private static final String dummyWorld = ".......W.......M...M";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource db) {
        jdbcTemplate = new JdbcTemplate(db);
    }

    @Autowired
    private StepUtils utils;

    @Override
    public void saveWorld(SpaceWorld world) {

    }

    void setJdbcTemplate(JdbcTemplate template){
        jdbcTemplate = template;
    }

    @Override
    public SpaceWorld loadWorld(int worldId) {
        List<SpaceWorld> worlds = this.jdbcTemplate.query("select worldid, startstep, startsegment, content from SPACE_WORLD where WORLDID = "+ worldId, new WorldRowMapper() );
        return worlds.get(0);
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
        //TODO implement World cache
        List<SpaceWorld> worlds = this.jdbcTemplate.query("select worldid, startstep, startsegment, content from SPACE_WORLD where WORLDID = "+ worldId, new WorldRowMapper());
        return worlds.get(0);
    }

    public void setUtils(StepUtils utils) {
        this.utils = utils;
    }

    class WorldRowMapper implements RowMapper<SpaceWorld> {
        public SpaceWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
            SpaceWorld world = utils.createWorldFromString(rs.getString("content"));
            world.setStartStep(rs.getInt("startstep"));
            world.setStartSegment(rs.getInt("startsegment"));
            world.setWorldId(rs.getInt("worldid"));
            return world;
        }
    }
}
