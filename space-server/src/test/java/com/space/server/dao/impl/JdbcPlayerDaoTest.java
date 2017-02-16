package com.space.server.dao.impl;

import com.space.server.domain.api.SpacePlayer;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by superernie77 on 11.02.2017.
 */
public class JdbcPlayerDaoTest {

    private JdbcPlayerDao dao;

    @Before
    public void setup(){
        dao = new JdbcPlayerDao();

        JdbcTemplate template = Mockito.mock(JdbcTemplate.class);
        List<Object> players = new ArrayList<>();
        players.add(mock(SpacePlayer.class));
        when(template.query(Mockito.anyString(), any(RowMapper.class))).thenReturn(players);

        dao.setJdbcTemplate(template);
    }

    @Test
    public void testGetPlayer(){
        SpacePlayer player = dao.getPlayer(1);
        Assert.assertNotNull(player);
    }

    @Test
    public void testRowMapper() throws SQLException {
        JdbcPlayerDao.PlayerRowMapper mapper = dao.new PlayerRowMapper();

        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("playerId")).thenReturn(2);

        SpacePlayer world = mapper.mapRow(rs, 0);

        Assert.assertTrue(world.getPlayerId() == 2);
    }
}
