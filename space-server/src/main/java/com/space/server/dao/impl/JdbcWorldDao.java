package com.space.server.dao.impl;

import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.SimpleSegment;
import com.space.server.domain.impl.SimpleWorldImpl;
import com.space.server.utils.SpaceUtils;
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
    private SpaceUtils utils;

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
        return null;
    }

    @Override
    public List<SpaceWorld> getWorlds() {
        return null;
    }

    @Override
    public SpaceWorld getWorld(int worldId) {

        List<SpaceWorld> worlds = this.jdbcTemplate.query("select worldid, startstep, startsegment from SPACE_WORLD where WORLDID = "+ worldId, new WorldRowMapper());

        SpaceWorld world = worlds.get(0);

        List<Segment> segments = this.jdbcTemplate.query("select worldid, segmentid, content from segment where worldid = "+ worldId +" order by naturalorder", new SegmentRowMapper());

        segments.stream().forEach(s -> world.addSegment(s) );

        return world;
    }

    public void setUtils(SpaceUtils utils) {
        this.utils = utils;
    }

    class WorldRowMapper implements RowMapper<SpaceWorld> {
        public SpaceWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
            SpaceWorld world = new SimpleWorldImpl();
            world.setStartStep(rs.getInt("startstep"));
            world.setStartSegment(rs.getInt("startsegment"));
            world.setWorldId(rs.getInt("worldid"));
            return world;
        }
    }

    class SegmentRowMapper implements RowMapper<Segment> {
        public Segment mapRow(ResultSet rs, int rowNum) throws SQLException {
            String content = rs.getString("content");
            return utils.createSegmentFromString(content);
        }
    }
}
