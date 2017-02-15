package com.space.server.web;

import com.google.gson.Gson;
import com.space.server.engine.api.ServerEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.impl.ServerEngineImpl;
import com.space.server.engine.impl.WorldEventImpl;
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
    private ServerEngine engine;
    private Gson gson = new Gson();

    public void setServerEngine(ServerEngine newEngine){
        engine = newEngine;
    }

    public ServerEngine getEngine(){ return engine;}

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
        if (engine == null) {
            engine = new SpringStarter().startSpringContext();
        }

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
