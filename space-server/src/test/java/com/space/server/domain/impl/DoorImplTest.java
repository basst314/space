package com.space.server.domain.impl;

import static org.junit.Assert.*;

import com.space.server.domain.api.Door;
import com.space.server.domain.api.Step;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 16.02.2017.
 */
public class DoorImplTest {

    private DoorImpl door;

    @Before
    public void setup(){

        Step step1 = new BasicStep();
        Step step2 = new BasicStep();

        door = new DoorImpl(step1,step2);
    }

    @Test
    public void testContent(){
        assertTrue(door.getContent().equals("D"));
        door.setContent("W");
        assertTrue(door.getContent().equals("W"));
    }

    @Test
    public void testSteps(){
        assertNotNull(door.getSourceStep());
        assertNotNull(door.getTargetStep());
    }
}


