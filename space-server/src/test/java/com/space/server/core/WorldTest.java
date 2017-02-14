package com.space.server.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by superernie77 on 10.02.2017.
 */
public class WorldTest {

    @Test
    public void testWorld(){
        World world = new World("test");

        Assert.assertTrue(world.getWorld().equals("test"));
    }
}
