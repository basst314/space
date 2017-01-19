package com.space.server.web;

import com.google.gson.Gson;
import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.impl.WorldEventImpl;
import com.space.server.web.util.JsonUtil;
import com.space.server.web.util.SpringStarter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.space.server.engine.api.WorldEventType.*;

/**
 * Handler for websocket events (connect, disconnect, sendMessage)
 * Created by superernie77 on 07.01.2017.
 */
@WebSocket
public class SpaceWebsocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SpaceWebsocketHandler.class);
    public GameEngine engine = new SpringStarter().startSpringContext();
    private Gson gson = new Gson();

    public void setGameEngine(GameEngine newEngine){
        engine = newEngine;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException, InterruptedException {
        LOG.debug("new connection: {}", session.getRemoteAddress());
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        WorldEvent event = gson.fromJson(message, WorldEventImpl.class);
        LOG.debug("received event: {}", event.getType());

        World gameWorld = null;

        if (event.getType().equals(START)) {
             engine.startGame(event.getPlayerId(), event.getWorldId());
             SpaceWorld world = engine.getWorld(event.getWorldId());
            gameWorld = new World(world.getSegment(0).getContent());
        } else if (event.getType().equals(STOP) ) {
            engine.stopGame(event.getPlayerId(), event.getWorldId());
        } else if (event.getType().equals(STEP)) {
            engine.stepWorld(event.getWorldId());
            SpaceWorld world = engine.getWorld(event.getWorldId());
            gameWorld = new World(world.getSegment(0).getContent());
        } else {
            SpaceWorld world = engine.getWorld(event.getWorldId());
            world.addEvent(event);
        }

        if (gameWorld != null) {
            WorldEvent resultEvent = new WorldEventImpl();
            resultEvent.setPlayerId(event.getPlayerId());
            resultEvent.setWorldId(event.getWorldId());
            resultEvent.setType(UPDATE);
            resultEvent.setWorld(gameWorld);

            LOG.debug("broadcasting gameworld... {}", event.getWorldId());
            broadcastWorld(session, resultEvent);
        }
    }

    public void broadcastWorld(Session session, WorldEvent world) {
        try {
            session.getRemote().sendString(JsonUtil.toJson(world));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
