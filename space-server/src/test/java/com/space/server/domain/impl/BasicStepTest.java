package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.domain.api.Step;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 08.12.2016.
 */
public class BasicStepTest {

    private Step step;

    @Before
    public void setup(){
        step = new BasicStep();
    }

    @Test
    public void testPrevious(){
        Step newStep = new BasicStep();

        step.setPrevious(newStep);

        Assert.assertEquals(newStep, step.previous());

    }

    @Test
    public void testNext(){
        Step newStep = new BasicStep();

        step.setNext(newStep);

        Assert.assertEquals(newStep, step.next());
    }

    @Test
    public void testContent(){

        // as default content contains a dot.
        Assert.assertEquals(step.getContent(),".");

        step.setContent("W");

        // now we have a weapon
        Assert.assertEquals(step.getContent(),"W");
    }
}
