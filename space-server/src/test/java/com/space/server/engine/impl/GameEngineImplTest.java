package com.space.server.engine.impl;

import static  org.mockito.Mockito.*;


import com.space.server.dao.api.PlayerDao;
import com.space.server.dao.api.WorldDao;
import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.api.Step;
import com.space.server.domain.impl.StepImpl;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test the gameEngine
 * Created by superernie77 on 11.12.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameEngineImplTest {

    @Mock
    private PlayerDao playerDao;

    @Mock
    private WorldDao worldDao;

    @InjectMocks
    private GameEngineImpl engine ;

    private SpacePlayer player;

    private SpaceWorld world;

    @Before
    public void setup(){
        player = mock(SpacePlayer.class);
        world = mock(SpaceWorld.class);

        when(playerDao.getPlayer(anyInt())).thenReturn(player);
        when(worldDao.getWorld(anyInt())).thenReturn(world);

        Segment seg = mock(Segment.class);
        when(world.getSegment(0)).thenReturn(seg);

        Step step = new StepImpl();
        when(seg.getStep(0)).thenReturn(step);
    }

    @Test
    public void testWorldSetup() throws Exception {

        engine.startGame(1,1);

        SpaceWorld world = engine.getWorld(1);

        Assert.assertEquals(world, this.world);

        SpacePlayer player = engine.getPlayer(1);

        Assert.assertEquals(player, this.player);
    }

    @Test
    public void testAddEvent() throws Exception {
        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(1);
        event.setWorldId(1);

        event.setType(WorldEventType.SPACE);

        engine.startGame(1,1);

        engine.addEvent(event);

        verify(world).addEvent(event);
    }
}