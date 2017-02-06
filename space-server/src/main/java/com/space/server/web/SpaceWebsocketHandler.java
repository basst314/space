package com.space.server.web;

import com.google.gson.Gson;
import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.impl.ServerEngineImpl;
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
    public ServerEngineImpl engine = new SpringStarter().startSpringContext();
    private Gson gson = new Gson();

    public void setGameEngine(ServerEngineImpl newEngine){
        engine = newEngine;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException, InterruptedException {
        LOG.debug("new connection: {}", session.getRemoteAddress());
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        LOG.debug("connection closed: {}", session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        WorldEvent event = gson.fromJson(message, WorldEventImpl.class);
        LOG.debug("received event: {}", event.getType());

        if (event.getType().equals(START)) {
             engine.startGame(event.getWorldId(),event.getPlayerId(), session);
        } else if (event.getType().equals(STOP) ) {
            engine.stopGame(event);
        } else {
           engine.addEvent(event);
        }
    }
}
