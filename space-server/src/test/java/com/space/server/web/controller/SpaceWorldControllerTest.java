package com.space.server.web.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import com.space.server.core.World;
import com.space.server.web.SpaceStarterWeb;
import com.space.server.web.util.SpringStarter;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by superernie77 on 13.02.2017.
 */
public class SpaceWorldControllerTest {

    private static SpringStarter starter = new SpringStarter();

    private SpaceWorldController controller;

    @Before
    public void setup(){
        SpaceStarterWeb.engine = starter.startSpringGameEngine();
    }

    /**
     * All rest operations should be passed by the proper events to the game engine.
     * Actual results are tested in the game engine tests.
     * @throws Exception
     */
    @Test
    public void testRestMethods() throws Exception {

        Request request = mock(Request.class);
        Response response = mock(Response.class);

        World world = (World)controller.start.handle(request, response);

        assertNotNull(world);

        world = (World) controller.space.handle(request,response);

        assertNotNull(world);

        world = (World) controller.doublespace.handle(request,response);

        assertNotNull(world);

        world = (World) controller.tripplespace.handle(request,response);

        assertNotNull(world);

        world = (World) controller.step.handle(request,response);

        assertNotNull(world);

        String stopped = (String) controller.stop.handle(request,response);

        assertNotNull(stopped);
    }

}
