package com.space.server.engine.impl;

import static org.mockito.Mockito.*;


import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Direction;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.*;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.utils.StepUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

    private WorldEventProcessor processor;

    private StepUtils utils = new StepUtils();

    @Before
    public void setupExampleWorld(){
        // Mock DAOs
        playerDao = mock(PlayerDao.class);
        worldDao = mock((WorldDao.class));
        processor = mock(WorldEventProcessor.class);


        // create real player
        player = new SpacePlayerImpl();
        player.setPlayerId(0);

        // create engine
        gameEngine = new GameEngineImpl();
        gameEngine.setWorldDao(worldDao);
        gameEngine.setPlayerDao(playerDao);
        gameEngine.setWorldEventProcessor(processor);


        // mock world init
        when(worldDao.getWorld(0)).thenReturn(exampleWorld);
        when(playerDao.getPlayer(0)).thenReturn(player);
    }


    @Test
    public void testHeroMovement(){

        exampleWorld = utils.createWorldFromString("........W........M");

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

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

    @Test
    public void testMoveBackwards(){
        exampleWorld = utils.createWorldFromString("......");

        exampleWorld.setStartSegment(0);
        exampleWorld.setStartStep(5);

        player.setDirection(Direction.BACKWARD);

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

        gameEngine.startGame(0,0);

        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);

        String worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertTrue(worldwithhero.equals("...H.."));
    }

    @Ignore
    @Test
    public void testTakeWeapon(){
        exampleWorld = utils.createWorldFromString("...W");

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

        gameEngine.startGame(0,0);

        String worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);

        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);

        gameEngine.addEvent(event);

        gameEngine.stepWorld(0);

        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertTrue(worldwithhero.equals("...H/"));
    }
}
