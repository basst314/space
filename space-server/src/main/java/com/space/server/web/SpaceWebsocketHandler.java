package com.space.server.web;

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
public class SpaceWebsocketHandler {

    private String msg;

    private Integer sender;

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException, InterruptedException {
        SpaceStarterWebsocket.userUsernameMap.put(session, Integer.valueOf(1));
        SpaceStarterWebsocket.broadcastWorld(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        Integer username = SpaceStarterWebsocket.userUsernameMap.get(session);
        SpaceStarterWebsocket.userUsernameMap.remove(session);
        SpaceStarterWebsocket.broadcastWorld(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        SpaceStarterWebsocket.broadcastWorld(session);
    }
}
