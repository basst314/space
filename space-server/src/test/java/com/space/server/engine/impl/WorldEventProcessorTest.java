package com.space.server.engine.impl;

import com.space.server.domain.api.Direction;
import com.space.server.domain.api.Item;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.impl.SpacePlayerImpl;
import com.space.server.domain.items.impl.Sword;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import org.assertj.core.internal.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the WorldEventProcessor
 * Created by superernie77 on 27.12.2016.
 */
public class WorldEventProcessorTest {

    private WorldEventProcessor processor;

    @Before
    public void setup(){
        processor = new WorldEventProcessor();
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
}
