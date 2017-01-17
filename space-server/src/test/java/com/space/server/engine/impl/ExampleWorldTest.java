package com.space.server.engine.impl;

import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Direction;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.impl.SpacePlayerImpl;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.utils.StepUtils;
import com.space.server.web.util.SpringStarter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests a complete world and a hero interacting with it.
 * Created by superernie77 on 16.12.2016.
 */
public class ExampleWorldTest {

    private GameEngineImpl gameEngine;

    private SpaceWorld exampleWorld;

    private PlayerDao playerDao;

    private WorldDao worldDao;

    private SpacePlayer player;

    private WorldEventProcessorImpl processor;

    private StepUtils utils = new StepUtils();

    private SpringStarter starter = new SpringStarter();

    @Before
    public void setupExampleWorld(){
        // Mock DAOs
        playerDao = mock(PlayerDao.class);
        worldDao = mock((WorldDao.class));
        processor = new WorldEventProcessorImpl();


        // create real player
        player = new SpacePlayerImpl();
        player.setPlayerId(0);

        // create engine
        gameEngine = (GameEngineImpl) starter.startSpringContext();
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

    @Test
    public void testMoveBackwardsWithWeapon() {
        exampleWorld = utils.createWorldFromString("....W.");

        exampleWorld.setStartSegment(0);
        exampleWorld.setStartStep(5); // ....WH

        player.setDirection(Direction.BACKWARD);

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

        gameEngine.startGame(0, 0);

        // take weapon
        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);
        gameEngine.addEvent(event);
        gameEngine.stepWorld(0); // .....\H

        String worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();
        Assert.assertTrue(worldwithhero.equals(".....\\H"));

        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);

        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertTrue(worldwithhero.equals("...\\H.."));
    }

    @Test
    public void testTakeWeapon(){
        exampleWorld = utils.createWorldFromString("...W");

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

        gameEngine.startGame(0,0);

        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);

        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);

        gameEngine.addEvent(event);

        gameEngine.stepWorld(0);

        String worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertEquals("..H/.", worldwithhero);
    }

    @Test
    public void testTakeWeaponHitMonster(){
        exampleWorld = utils.createWorldFromString("...WM");

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

        gameEngine.startGame(0,0);

        // step to weapon
        gameEngine.stepWorld(0);
        gameEngine.stepWorld(0);

        // take weapon
        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);
        gameEngine.addEvent(event);
        gameEngine.stepWorld(0);

        // step to monster
        gameEngine.stepWorld(0);

        // hit monster
        event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);

        gameEngine.addEvent(event);
        gameEngine.stepWorld(0);

        String worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();

        Assert.assertEquals("...H-.", worldwithhero);
    }

    @Test
    public void testTakeWeaponHitEmpty() {
        String worldwithhero;
        exampleWorld = utils.createWorldFromString("..W..");

        when(worldDao.getWorld(0)).thenReturn(exampleWorld);

        gameEngine.startGame(0, 0);

        // step to weapon
        gameEngine.stepWorld(0); // .HW..

        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();
        Assert.assertEquals(".HW..", worldwithhero);

        // take weapon
        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);
        gameEngine.addEvent(event);
        gameEngine.stepWorld(0);

        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();
        Assert.assertEquals(".H/...", worldwithhero);

        // step
        gameEngine.stepWorld(0);

        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();
        Assert.assertEquals("..H/..", worldwithhero);

        // hit empty path
        event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);

        gameEngine.addEvent(event);
        gameEngine.stepWorld(0);

        // item is in used-mode
        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();
        Assert.assertEquals("..H-..", worldwithhero);

        // step
        gameEngine.stepWorld(0);

        // item back to normal + hero moved
        worldwithhero = gameEngine.getWorld(0).getSegment(0).getContent();
        Assert.assertEquals("...H/.", worldwithhero);
    }
}