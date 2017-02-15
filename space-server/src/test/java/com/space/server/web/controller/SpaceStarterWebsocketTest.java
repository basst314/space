package com.space.server.web.controller;

import com.space.server.web.SpaceStarterWebsocket;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

/**
 * Created by superernie77 on 15.02.2017.
 */
public class SpaceStarterWebsocketTest {

    @Test
    public void testStart(){

        String[] args = new String[1];
        args[0] = "8090";
        SpaceStarterWebsocket.main(args);

        Assert.assertTrue(SpaceStarterWebsocket.port == 8090);
    }

    @After
    public  void stop() throws Exception{
        Spark.stop();

        // wait for one second for the server to stop
        Thread.sleep(1000l);

    }
}
