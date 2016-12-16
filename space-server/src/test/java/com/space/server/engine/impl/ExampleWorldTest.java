package com.space.server.engine.impl;

import static org.mockito.Mockito.*;


import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Overlay;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.*;
import com.space.server.domain.items.impl.Sword;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Test a complete world and a hero interacting with it.
 * Created by superernie77 on 16.12.2016.
 */
public class ExampleWorldTest {

    private GameEngineImpl gameEngine;

    private SpaceWorld exampleWorld;

    private PlayerDao playerDao;

    private WorldDao worldDao;

    private SpacePlayer player;

    private String gameWorldString = "........W........M";


    private SpaceWorld createWorldFromString(String worldString) {
        SimpleWorldImpl world = new SimpleWorldImpl();
        world.setWorldId(0);

        SimpleSegment segment = new SimpleSegment();

        world.addSegment(segment);

        String[] chars = worldString.split("");

        List<String> worldChars = Arrays.asList(chars);

        for (String tocken : worldChars) {
            BasicStep step = new BasicStep();
            Overlay over = null;

            if (tocken.equals("W")) {
                over = new Sword();
            } else if (tocken.equals("M")) {
                over = new BasicMonster();
            }
            if (over != null) {
                step.addOverlay(over);
            }
            segment.addStep(step);
        }

        // just to make sure the world was setup properly
        assert world.getSegment(0).getContent().equals(gameWorldString);

        return world;
    }

    @Before
    public void setupExampleWorld(){
        // Mock DAOs
        playerDao = mock(PlayerDao.class);
        worldDao = mock((WorldDao.class));

        // create real player
        player = new SpacePlayerImpl();

        // create engine
        gameEngine = new GameEngineImpl();
        gameEngine.setWorldDao(worldDao);
        gameEngine.setPlayerDao(playerDao);

        // create world
        exampleWorld = createWorldFromString(gameWorldString);

        // mock world init
        when(worldDao.getWorld(0)).thenReturn(exampleWorld);
        when(playerDao.getPlayer(0)).thenReturn(player);
    }


    @Test
    public void testHeroMovement(){

        gameEngine.startGame(0,0);

        String worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertTrue(worldwithhero.equals("H.......W........M"));

        // three steps foreward
        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);

        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertTrue(worldwithhero.equals("...H....W........M"));
    }

}
