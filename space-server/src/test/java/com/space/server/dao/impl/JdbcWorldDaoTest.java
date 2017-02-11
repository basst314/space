package com.space.server.dao.impl;

import static org.mockito.Mockito.*;

import com.space.server.dao.impl.JdbcWorldDao;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.SimpleWorldImpl;
import com.space.server.utils.StepUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by superernie77 on 11.02.2017.
 */
public class JdbcWorldDaoTest {

    private JdbcWorldDao dao;

    private StepUtils utils;

    @Before
    public void setup(){
        dao = new JdbcWorldDao();

        utils = mock(StepUtils.class);

        JdbcTemplate template = Mockito.mock(JdbcTemplate.class);
        List<Object> worlds = new ArrayList<>();
        worlds.add(mock(SpaceWorld.class));
        when(template.query(Mockito.anyString(), any(RowMapper.class))).thenReturn(worlds);

        dao.setUtils(utils);
        dao.setJdbcTemplate(template);
    }

    @Test
    public void testLoadWorld(){
        SpaceWorld world = dao.loadWorld(1);
        Assert.assertNotNull(world);
    }

    @Test
    public void testGetWorld(){
        SpaceWorld world = dao.getWorld(1);
        Assert.assertNotNull(world);
    }

    @Test
    public void testRowMapper() throws SQLException {
        JdbcWorldDao.WorldRowMapper mapper = dao.new WorldRowMapper();

        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("startstep")).thenReturn(1);
        when(rs.getInt("startsegment")).thenReturn(5);
        when(rs.getInt("worldid")).thenReturn(2);
        when(rs.getString("content")).thenReturn("H...W");
        when(utils.createWorldFromString(anyString())).thenReturn(new SimpleWorldImpl());

        SpaceWorld world = mapper.mapRow(rs, 0);

        Assert.assertTrue(world.getStartStep() == 1);
        Assert.assertTrue(world.getStartSegment() == 5);
        Assert.assertTrue(world.getWorldId() == 2);
    }
}
