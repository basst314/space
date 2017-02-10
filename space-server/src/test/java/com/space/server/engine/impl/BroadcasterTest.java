package com.space.server.engine.impl;

import com.space.server.engine.api.GameEngine;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by superernie77 on 10.02.2017.
 */
public class BroadcasterTest {

    @Test
    public void testBroadCaster(){
        Broadcaster broadcaster = new Broadcaster();

        broadcaster.setEngine(Mockito.mock(GameEngine.class));

        broadcaster.setPlayerId(1);

        broadcaster.setWorldId(0);

        Assert.assertTrue(broadcaster.getPlayerId() == 1);

        Assert.assertTrue(broadcaster.getWorldId() == 0);

        Assert.assertTrue(broadcaster.getEngine() != null);
    }
}
