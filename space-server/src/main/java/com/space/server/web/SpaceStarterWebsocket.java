package com.space.server.web;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Created by superernie77 on 26.10.2016.
 */
@WebSocket
public class SpaceStarterWebsocket {

    @OnWebSocketMessage
    public void tweets(final String message, Session session) throws IOException, InterruptedException {
        int i = 0;
        if (session.isOpen()) {
            String response = message.toUpperCase();
            session.getRemote().sendString(response);
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
    }


    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
    }

}
