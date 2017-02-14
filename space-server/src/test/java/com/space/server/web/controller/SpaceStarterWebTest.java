package com.space.server.web.controller;

import com.space.server.engine.api.GameEngine;
import com.space.server.web.SpaceStarterWeb;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by superernie77 on 14.02.2017.
 */
public class SpaceStarterWebTest {

    private SpaceStarterWeb starter;

    private GameEngine engine;

    @Test
    public void testWebStart(){

        String[] args = new String[0];

        starter.main(args);

        engine = starter.engine;

        Assert.assertNotNull(engine);

    }

    @After
    public void shutdown(){
        engine.shutdownDatabase();
    }
}
