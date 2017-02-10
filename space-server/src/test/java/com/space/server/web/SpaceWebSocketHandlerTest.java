package com.space.server.web;

import com.google.gson.Gson;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.engine.impl.WorldEventImpl;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

/**
 * Test the websocket server.
 * Created by superernie77 on 11.01.2017.
 */
public class SpaceWebSocketHandlerTest {

    private SpaceWebsocketHandler handler ;
    private Gson gson = new Gson();

    @Before
    public void setup(){
        handler = new SpaceWebsocketHandler();
    }

    @After
    public void shutdown() {
        handler.engine.shutdownDatabase();
    }

    @Ignore
    @Test
    public void testWorldScenario() throws IOException{

        RemoteEndpoint enpdpoint = Mockito.mock(RemoteEndpoint.class);
        Session session = Mockito.mock(Session.class);
        Mockito.when(session.getRemote()).thenReturn(enpdpoint);

        // 1. first we start the game by a startEvent
        WorldEvent startEvent = new WorldEventImpl();
        startEvent.setPlayerId(0);
        startEvent.setWorldId(0);
        startEvent.setType(WorldEventType.START);

        String message = gson.toJson(startEvent);

        handler.onMessage(session, message);

        // world in json format has been broadcastet
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\"³H......W.......M¹...M¹\"}}");


        // 2. secondly, we move 6 times to the weapon
        WorldEvent stepEvent = new WorldEventImpl();
        stepEvent.setPlayerId(0);
        stepEvent.setWorldId(0);
        stepEvent.setType(WorldEventType.STEP);

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\".³H.....W.......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\"..³H....W.......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\"...³H...W.......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\"....³H..W.......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\".....³H.W.......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        // hero is now in front of weapon
        Mockito.verify(enpdpoint).sendString(
                "{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                        "world\":{\"world\":\"......³HW.......M¹...M¹\"}}");

        // 3. thirdly, we pick up the weapon
        WorldEvent spaceEvent = new WorldEventImpl();
        spaceEvent.setPlayerId(0);
        spaceEvent.setWorldId(0);
        spaceEvent.setType(WorldEventType.SPACE);

        handler.onMessage(session, gson.toJson(spaceEvent));
        handler.onMessage(session, gson.toJson(stepEvent));

        // hero is now in possesion of the mighty sword
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"......³H/........M¹...M¹\"}}");

        // 4. move to the first monster
        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\".......³H/.......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"........³H/......M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\".........³H/.....M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"..........³H/....M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"...........³H/...M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"............³H/..M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\".............³H/.M¹...M¹\"}}");

        handler.onMessage(session, gson.toJson(stepEvent));
        // hero is in front of first monster
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"..............³H/M¹...M¹\"}}");

        // 5. hit monster

        handler.onMessage(session, gson.toJson(spaceEvent));
        handler.onMessage(session, gson.toJson(stepEvent));

        // hero killed first monster
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"..............³H-....M¹\"}}");

        // 6. run away from second monster
        WorldEvent doubleSpaceEvent = new WorldEventImpl();
        doubleSpaceEvent.setPlayerId(0);
        doubleSpaceEvent.setWorldId(0);
        doubleSpaceEvent.setType(WorldEventType.DOUBLE_SPACE);

        handler.onMessage(session, gson.toJson(doubleSpaceEvent));
        handler.onMessage(session, gson.toJson(stepEvent));

        // weapon points in opposite direction
        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\".............\\\\H³.....M¹\"}}");

        // 7. move one more step
        handler.onMessage(session, gson.toJson(stepEvent));

        Mockito.verify(enpdpoint).sendString("{\"worldId\":0,\"playerId\":0,\"worldEventType\":\"UPDATE\",\"" +
                "world\":{\"world\":\"............\\\\H³......M¹\"}}");
    }
}