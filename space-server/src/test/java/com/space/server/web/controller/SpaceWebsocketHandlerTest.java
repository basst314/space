package com.space.server.web.controller;

import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.space.server.engine.api.ServerEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.engine.impl.WorldEventImpl;
import com.space.server.web.SpaceWebsocketHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 14.02.2017.
 */
public class SpaceWebsocketHandlerTest {

    private SpaceWebsocketHandler handler;

    @Before
    public void setup(){
        handler = new SpaceWebsocketHandler();

        ServerEngine serverengine = mock(ServerEngine.class);

        handler.setServerEngine(serverengine);
    }

    @Test
    public void testOnMessageStart() throws Exception{

        WorldEventImpl event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.START);

        Session session = mock(Session.class);

        handler.onMessage(session, new Gson().toJson(event));

        verify(handler.getEngine()).startGame(anyInt(),anyInt(),any(Session.class));
    }

    @Test
    public void testOnMessageStop() throws Exception{

        WorldEventImpl event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.STOP);

        Session session = mock(Session.class);

        handler.onMessage(session, new Gson().toJson(event));

        verify(handler.getEngine()).stopGame(any(WorldEvent.class));
    }

    @Test
    public void testOnMessageAddEvent() throws Exception{

        WorldEventImpl event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.SPACE);

        Session session = mock(Session.class);

        handler.onMessage(session, new Gson().toJson(event));

        verify(handler.getEngine()).addEvent(any(WorldEvent.class));
    }
}
