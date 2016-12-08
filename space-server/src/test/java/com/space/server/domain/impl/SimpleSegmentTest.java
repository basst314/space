package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.Step;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 08.12.2016.
 */
public class SimpleSegmentTest {

    private Segment segment;

    @Before
    public void setup(){
        segment = new SimpleSegment();
    }

    @Test
    public void testAddStep(){
        Step first = new BasicStep();
        Step second = new BasicStep();
        Step third = new BasicStep();

        segment.addStep(first).addStep(second).addStep(third);

        // test if chaining is properly set
        Assert.assertTrue(second.previous().equals(first));
        Assert.assertTrue(second.next().equals(third));
    }

    @Test
    public void testGetContent(){
        Step first = new BasicStep();
        Step second = new BasicStep();
        Step third = new BasicStep();

        segment.addStep(first).addStep(second).addStep(third);

        String content = segment.getContent();

        // three emppty steps
        Assert.assertTrue(content.equals("..."));
    }
}
