package com.space.server.web;

import com.google.gson.Gson;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.engine.impl.WorldEventImpl;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

/**
 * Test the websocket client.
 * Created by superernie77 on 11.01.2017.
 */
public class SpaceWebSocketHandlerTest {

    private SpaceWebsocketHandler handler ;
    private Gson gson = new Gson();

    @Before
    public void setup(){
        handler = new SpaceWebsocketHandler();
    }

    @Test
    public void testStartGame() throws IOException{

        RemoteEndpoint enpdpoint = Mockito.mock(RemoteEndpoint.class);
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.getRemote()).thenReturn(enpdpoint);

        WorldEvent event = new WorldEventImpl();
        event.setPlayerId(0);
        event.setWorldId(0);
        event.setType(WorldEventType.START);

        String message = gson.toJson(event);

        handler.onMessage(session, message);

        Mockito.verify(enpdpoint).sendString(Mockito.anyString());

    }
}
