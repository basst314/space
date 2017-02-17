package com.space.server.dao.impl;

import static org.mockito.Mockito.*;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.SegmentImpl;
import com.space.server.domain.impl.SpaceWorldImpl;
import com.space.server.utils.SpaceUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by superernie77 on 11.02.2017.
 */
public class JdbcWorldDaoTest {

    private JdbcWorldDao dao;

    private SpaceUtils utils;

    @Before
    public void setup(){
        dao = new JdbcWorldDao();

        utils = mock(SpaceUtils.class);

        JdbcTemplate template = Mockito.mock(JdbcTemplate.class);
        List<SpaceWorld> worlds = new ArrayList<>();
        worlds.add(mock(SpaceWorld.class));
        when(template.query(Mockito.anyString(), any(JdbcWorldDao.WorldRowMapper.class))).thenReturn(worlds);


        dao.setUtils(utils);
        dao.setJdbcTemplate(template);
    }

    @Test
    public void testGetWorld(){
        SpaceWorld world = dao.getWorld(1);
        Assert.assertNotNull(world);
    }

    @Test
    public void testWorldRowMapper() throws SQLException {
        JdbcWorldDao.WorldRowMapper mapper = dao.new WorldRowMapper();

        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("startstep")).thenReturn(1);
        when(rs.getInt("startsegment")).thenReturn(5);
        when(rs.getInt("worldid")).thenReturn(2);
        when(utils.createWorldWithSingleSegment(anyString())).thenReturn(new SpaceWorldImpl());

        SpaceWorld world = mapper.mapRow(rs, 0);

        Assert.assertTrue(world.getStartStep() == 1);
        Assert.assertTrue(world.getStartSegment() == 5);
        Assert.assertTrue(world.getWorldId() == 2);
    }

    @Test
    public void testSegmentRowMapper() throws SQLException {
        JdbcWorldDao.SegmentRowMapper mapper = dao.new SegmentRowMapper();

        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("content")).thenReturn(".....");
        when(utils.createSegmentFromString(anyString())).thenReturn(new SegmentImpl());

        Segment segment = mapper.mapRow(rs, 0);

        Assert.assertNotNull(segment);
    }
}
