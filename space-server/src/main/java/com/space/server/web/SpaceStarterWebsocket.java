package com.space.server.web;

import org.eclipse.jetty.websocket.api.Session;
import spark.Spark;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.*;

/**
 * Starts the space websocket server component
 * Created by superernie77 on 26.10.2016.
 */

public class SpaceStarterWebsocket {

    public static int port = 8080;

    public static void main(String[] args) {

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