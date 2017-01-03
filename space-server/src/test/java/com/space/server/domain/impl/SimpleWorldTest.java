package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.impl.WorldEventImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by superernie77 on 08.12.2016.
 */
public class SimpleWorldTest {

    private SpaceWorld world;

    @Before
    public void setup(){
        world = new SimpleWorldImpl();
    }

    @Test
    public void testAddSegment(){

        Segment segment = new SimpleSegment();

        world.addSegment(segment);

        Assert.assertEquals(segment, world.getSegment(0));



    }

    @Test
    public void testAddRemoveEvent(){

        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        WorldEvent event2 = new WorldEventImpl();
        event2.setPlayerId(0);
        WorldEvent event3 = new WorldEventImpl();
        event3.setPlayerId(0);

        world.addEvent(event);
        world.addEvent(event2);
        world.addEvent(event3);

        List<WorldEvent> events = Arrays.asList(event2,event3);

        world.removeEvents(events);

        Assert.assertTrue(world.getEventsForPlayer(0).get(0).equals(event));
    }


}
