package com.space.server.engine.impl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by superernie77 on 11.02.2017.
 */
public class ServerEngineImplTest {

    private ServerEngineImpl serverEngine;

    private GameEngine gameEngine;

    private ScheduledExecutorService executorService;

    @Before
    public void setup(){
        serverEngine = new ServerEngineImpl();

        gameEngine = mock(GameEngine.class);

        executorService = mock(ScheduledExecutorService.class);
        when(executorService.scheduleAtFixedRate(any(Runnable.class),anyLong(),anyLong(),any(TimeUnit.class))).thenReturn(mock(ScheduledFuture.class));

        serverEngine.setEngine(gameEngine);
        serverEngine.setScheduledExecutorService(executorService);
    }

    @Test
    public void testDBSchutdown(){
        serverEngine.shutdownDatabase();
        verify(gameEngine,times(1)).shutdownDatabase();
    }

    @Test
    public void testAddEvent(){
        WorldEvent event = mock(WorldEvent.class);

        SpaceWorld world = mock(SpaceWorld.class);
        when(gameEngine.getWorld(anyInt())).thenReturn(world);

        serverEngine.addEvent(event);

        verify(world,times(1)).addEvent(event);
    }

    @Test
    public void testStopGame(){
        WorldEvent event = mock(WorldEvent.class);

        serverEngine.stopGame(event);

        verify(gameEngine,times(1)).stopGame(anyInt(),anyInt());
    }

    @Test
    public void testStopBroadcast(){

        WorldEvent event = new WorldEventImpl();
        event.setWorldId(0);
        event.setPlayerId(0);
        event.setType(WorldEventType.STOP);

        // Init a world started with one player
        Set<Integer> player = new HashSet<>();
        player.add(0);
        serverEngine.getPlayerWorldMap().put(0, player);
        serverEngine.getWorldFutureMap().put(0, mock(ScheduledFuture.class));
        serverEngine.getPlayerSessionMap().put(0, mock(Session.class));

        serverEngine.stopGame(event);

        // world with player has been stoped
        assertTrue(serverEngine.getPlayerWorldMap().size() == 0);
        assertTrue(serverEngine.getWorldFutureMap().size() == 0);
        assertTrue(serverEngine.getPlayerSessionMap().size() == 0);
    }

    @Test
    public void testStartGame(){

        serverEngine.startGame(0,0, mock(Session.class));

        // world with player has been created and started to run
        assertTrue(serverEngine.getPlayerWorldMap().size() == 1);
        assertTrue(serverEngine.getWorldFutureMap().size() == 1);
        assertTrue(serverEngine.getPlayerSessionMap().size() == 1);
    }

    @Test
    public void startGameTwice(){
        serverEngine.startGame(0,0, mock(Session.class));
        serverEngine.startGame(0,0, mock(Session.class));

        // game only started once
        assertTrue(serverEngine.getPlayerWorldMap().size() == 1);
        assertTrue(serverEngine.getWorldFutureMap().size() == 1);
        assertTrue(serverEngine.getPlayerSessionMap().size() == 1);
    }

    @Test
    public void startGameWith2Players(){
        serverEngine.startGame(0,0, mock(Session.class));
        serverEngine.startGame(0,1, mock(Session.class));

        // one world started
        assertTrue(serverEngine.getPlayerWorldMap().size() == 1);

        // two player inside world 0
        assertTrue(serverEngine.getPlayerWorldMap().get(0).size() == 2);

        // one future
        assertTrue(serverEngine.getWorldFutureMap().size() == 1);

        // two player
        assertTrue(serverEngine.getPlayerSessionMap().size() == 2);
    }

}