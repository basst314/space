package com.space.server.engine.impl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
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
        when(gameEngine.getWorld(anyInt())).thenReturn(mock(SpaceWorld.class));
        Session session = mock(Session.class);
        when(session.getRemote()).thenReturn(mock(RemoteEndpoint.class));

        serverEngine.startGame(0,0, session);

        // world with player has been created and started to run
        assertTrue(serverEngine.getPlayerWorldMap().size() == 1);
        assertTrue(serverEngine.getWorldFutureMap().size() == 1);
        assertTrue(serverEngine.getPlayerSessionMap().size() == 1);

        // broadcasted once
        verify(session, times(1)).getRemote();
    }

    @Test
    public void startGameTwice(){
        when(gameEngine.getWorld(anyInt())).thenReturn(mock(SpaceWorld.class));
        Session session = mock(Session.class);
        when(session.getRemote()).thenReturn(mock(RemoteEndpoint.class));


        serverEngine.startGame(0,0, session);
        serverEngine.startGame(0,0, session);

        // game only started once
        assertTrue(serverEngine.getPlayerWorldMap().size() == 1);
        assertTrue(serverEngine.getWorldFutureMap().size() == 1);
        assertTrue(serverEngine.getPlayerSessionMap().size() == 1);

        verify(session, times(1)).getRemote();
    }

    @Test
    public void startGameWith2Players(){

        when(gameEngine.getWorld(anyInt())).thenReturn(mock(SpaceWorld.class));
        Session session = mock(Session.class);
        when(session.getRemote()).thenReturn(mock(RemoteEndpoint.class));

        serverEngine.startGame(0,0, session);
        serverEngine.startGame(0,1, session);

        // one world started
        assertTrue(serverEngine.getPlayerWorldMap().size() == 1);

        // two player inside world 0
        assertTrue(serverEngine.getPlayerWorldMap().get(0).size() == 2);

        // one future
        assertTrue(serverEngine.getWorldFutureMap().size() == 1);

        // two player
        assertTrue(serverEngine.getPlayerSessionMap().size() == 2);

        // world started for player 1
        verify(gameEngine,times(1)).startGame(anyInt(),anyInt());

        // player 2 added to world
        verify(gameEngine,times(1)).addPlayer2World(anyInt(),anyInt());

        // start game broadcasted once
        verify(session, times(2)).getRemote();
    }

    @Test
    public void testRunner() throws Exception {
        ServerEngineImpl.Runner runner = serverEngine.new Runner();

        // player in world 0
        Set<Integer> player = new HashSet<>();
        player.add(0);
        player.add(1);
        serverEngine.getPlayerWorldMap().put(0, player);

        serverEngine.getWorldFutureMap().put(0, mock(ScheduledFuture.class));
        serverEngine.getPlayerSessionMap().put(0, mock(Session.class));
        serverEngine.getPlayerSessionMap().put(1, mock(Session.class));

        // create broadcaster
        Broadcaster b = mock(Broadcaster.class);
        when(b.createWorldEvent(anyInt())).thenReturn(new WorldEventImpl());
        when(b.getEngine()).thenReturn(gameEngine);
        runner.setBroadCaster(b);

        // broadcast
        runner.run();

        // world has been broadcast by the runner the players
        verify(b, times(2)).broadcast(any(Session.class), any(WorldEvent.class));
    }

}
