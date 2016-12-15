package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
