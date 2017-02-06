package com.space.server.web;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.*;

/**
 * Starts the space websocket server component
 * Created by superernie77 on 26.10.2016.
 */

public class SpaceStarterWebsocket {

    // maps the websocket session against the space player id
    static Map<Session, Integer> userUsernameMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        int port = 8080;
        if (args.length > 0) {
           port = Integer.parseInt(args[0]);
        }
   		port(port);
	    // LOG.info("Server running on port {}", port);

        staticFileLocation("/static");
        staticFiles.expireTime(600L);

	    webSocket("/api", SpaceWebsocketHandler.class);

        init();
    }
}