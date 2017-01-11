package com.space.server.web;

import static com.space.server.engine.api.WorldEventType.*;

import com.google.gson.Gson;
import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.engine.impl.GameEngineImpl;
import com.space.server.engine.impl.WorldEventImpl;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Handler for websocket events (connect, disconnect, sendMessage)
 * Created by superernie77 on 07.01.2017.
 */
@WebSocket
class SpaceWebsocketHandler {

    private Gson gson = new Gson();

    public GameEngine engine = new GameEngineImpl();

    public void setGameEngine(GameEngine newEngine){
        engine = newEngine;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException, InterruptedException {
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {

        WorldEvent event = gson.fromJson(message, WorldEventImpl.class);

        World result = null;
         if (event.getType().equals(START) ) {
             engine.startGame(event.getPlayerId(), event.getWorldId());
             SpaceWorld world = SpaceStarterWeb.engine.getWorld(event.getWorldId());
             result = new World(world.getSegment(0).getContent());
        } else if (event.getType().equals(STOP) ) {
            engine.stopGame(event.getPlayerId(), event.getWorldId());
        } else if (event.getType().equals(STEP)) {
            SpaceStarterWeb.engine.stepWorld(event.getWorldId());
            SpaceWorld world = SpaceStarterWeb.engine.getWorld(event.getWorldId());
            result = new World(world.getSegment(0).getContent());
        } else {
            SpaceWorld world = SpaceStarterWeb.engine.getWorld(event.getWorldId());
            world.addEvent(event);
            result = new World(world.getSegment(0).getContent());
        }
        SpaceStarterWebsocket.broadcastWorld(session, result );
    }
}
