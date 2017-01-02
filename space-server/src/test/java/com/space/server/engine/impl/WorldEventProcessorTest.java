package com.space.server.engine.impl;

import static org.mockito.Mockito.*;

import com.space.server.domain.api.*;
import com.space.server.domain.impl.BasicStep;
import com.space.server.domain.impl.SpacePlayerImpl;
import com.space.server.domain.items.impl.Sword;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test the WorldEventProcessorImpl
 * Created by superernie77 on 27.12.2016.
 */
public class WorldEventProcessorTest {

    private WorldEventProcessorImpl processor;

    @Before
    public void setup(){
        processor = new WorldEventProcessorImpl();
    }

    @Test
    public void testDoubleSpaceForward(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.DOUBLE_SPACE);

        SpacePlayer player = new SpacePlayerImpl();
        player.setDirection(Direction.BACKWARD);

        processor.processEvents(java.util.Arrays.asList(event),player);

        Assert.assertTrue(player.getDirection() == Direction.FORWARD);
    }

    @Test
    public void testDoubleSpaceBackward(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.DOUBLE_SPACE);

        SpacePlayer player = new SpacePlayerImpl();
        player.setDirection(Direction.FORWARD);

        processor.processEvents(java.util.Arrays.asList(event),player);

        Assert.assertTrue(player.getDirection() == Direction.BACKWARD);
    }

    @Test
    public void testTrippleSpace1(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.TRIPPLE_SPACE);

        SpacePlayer player = new SpacePlayerImpl();

        Item one = new Sword();
        Item two = new Sword();
        two.setContent(")");

        player.addItem(one);
        player.addItem(two);

        player.setActiveItem(0);

        processor.processEvents(java.util.Arrays.asList(event),player);

        Assert.assertTrue(player.getActiveItem().getContent().equals(")"));
    }

    @Test
    public void testTrippleSpace2(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.TRIPPLE_SPACE);

        SpacePlayer player = new SpacePlayerImpl();

        Item one = new Sword();
        Item two = new Sword();
        two.setContent(")");

        player.addItem(one);
        player.addItem(two);

        player.setActiveItem(1);

        processor.processEvents(java.util.Arrays.asList(event),player);

        Assert.assertTrue(player.getActiveItem().getContent().equals("W"));
    }

    @Test
    public void testPickupWeapon(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.TRIPPLE_SPACE);

        SpacePlayer player = new SpacePlayerImpl();

        Item one = new Sword();
        Item two = new Sword();
        two.setContent(")");

        player.addItem(one);
        player.addItem(two);

        player.setActiveItem(1);

        processor.processEvents(java.util.Arrays.asList(event),player);

        Assert.assertTrue(player.getActiveItem().getContent().equals("W"));
    }

    @Test
    public void testHitMonster(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.SPACE);

        SpacePlayer player = new SpacePlayerImpl();

        Step currentStep = new BasicStep();
        Step nextStep = new BasicStep();

        player.setActiveStep(currentStep);
        player.addItem(new Sword());
        player.setActiveItem(0);
        currentStep.setNext(nextStep);

        Monster monster = mock(Monster.class);
        nextStep.addOverlay(monster);

        processor.processEvents(Arrays.asList(event),player);

        // monster is dead
        Assert.assertTrue(player.getActiveStep().next().getOverlays().size() == 0);
    }

    @Test
    public void testNoHit(){

        WorldEvent event = new WorldEventImpl();
        event.setType(WorldEventType.SPACE);

        SpacePlayer player = new SpacePlayerImpl();

        Step currentStep = new BasicStep();
        Step nextStep = new BasicStep();

        player.setActiveStep(currentStep);
        currentStep.setNext(nextStep);

        Monster monster = mock(Monster.class);
        nextStep.addOverlay(monster);

        processor.processEvents(Arrays.asList(event),player);

        // monster is not dead because player didn't have a weapon
        Assert.assertTrue(player.getActiveStep().next().getOverlays().size() == 1);
    }
}
