package com.space.server.dao.impl;

import com.space.server.dao.api.PlayerDao;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.impl.SpacePlayerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Dummy implementation of the PlayerDao for testing.
 * Created by superernie77 on 01.01.2017.
 */
@Repository
public class JdbcPlayerDao implements PlayerDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource db) {
        jdbcTemplate = new JdbcTemplate(db);
    }

    @Override
    public SpacePlayer getPlayer(int playerId) {
        List<SpacePlayer> actors = this.jdbcTemplate.query(
                "select PLAYERID from PLAYER where PLAYERID = "+ playerId,
                new RowMapper<SpacePlayer>() {
                    public SpacePlayer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        SpacePlayer actor = new SpacePlayerImpl();
                        actor.setPlayerId(rs.getInt("playerId"));
                        return actor;
                    }
                });
		return actors.get(0);
	}

    @Override
    public void savePlayer(SpacePlayer player) {

    }
}
