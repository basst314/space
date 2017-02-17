package com.space.server.dao.impl;

import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Door;
import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.api.Step;
import com.space.server.domain.impl.DoorImpl;
import com.space.server.domain.impl.SpaceWorldImpl;
import com.space.server.utils.SpaceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    void setJdbcTemplate(JdbcTemplate template){
        jdbcTemplate = template;
    }

    @Override
    public SpaceWorld getWorld(int worldId) {

        // Select the world
        List<SpaceWorld> worlds = this.jdbcTemplate.query("select worldid, startstep, startsegment from SPACE_WORLD where WORLDID = "+ worldId, new WorldRowMapper());
        SpaceWorld world = worlds.get(0);

        // create segments and add to world
        List<Segment> segments = this.jdbcTemplate.query("select worldid, segmentid, content from segment where worldid = "+ worldId +" order by naturalorder", new SegmentRowMapper());
        segments.forEach(s -> world.addSegment(s) );

        // create doors and add to segments. mapper does the adding internally
        this.jdbcTemplate.query("select sourcesegment, targetsegment, sourcestep, targetstep, worldid from segment_door where worldid = "+ worldId, new DoorRowMapper(segments));

        return world;
    }

    public void setUtils(SpaceUtils utils) {
        this.utils = utils;
    }

    class WorldRowMapper implements RowMapper<SpaceWorld> {
        public SpaceWorld mapRow(ResultSet rs, int rowNum) throws SQLException {
            SpaceWorld world = new SpaceWorldImpl();
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

    class DoorRowMapper implements RowMapper<Door> {

        private List<Segment> segments;

        public DoorRowMapper(List<Segment>  segments){
            this.segments = segments;
        }

        public Door mapRow(ResultSet rs, int rowNum) throws SQLException {

            int startsegment = rs.getInt(1);
            int targetsegment = rs.getInt(2);
            int startstep = rs.getInt(3);
            int targetstep = rs.getInt(4);

            Step step1 = segments.get(startsegment).getStep(startstep);
            Step step2 = segments.get(targetsegment).getStep(targetstep);

            Door door = new DoorImpl(step1,step2);

            step1.addOverlay(door);
            step2.addOverlay(door);

            return door;
        }
    }
}
