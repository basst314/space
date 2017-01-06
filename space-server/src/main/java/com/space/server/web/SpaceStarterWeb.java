package com.space.server.web;

import com.space.server.engine.api.GameEngine;
import com.space.server.engine.impl.GameEngineImpl;
import com.space.server.web.controller.SpaceWorldController;
import com.space.server.web.util.Filters;
import com.space.server.web.util.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.space.server.web.util.JsonUtil.json;
import static spark.Spark.*;

/**
 *  Static classes that handle all REST requets.
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class SpaceStarterWeb {

	private static final Logger LOG = LoggerFactory.getLogger(SpaceStarterWeb.class);

	public static GameEngine engine = new GameEngineImpl();

	public static void main(String[] args) {
		int port = 8080;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		port(port);
		LOG.info("Server running on port {}", port);

		staticFiles.location("/static");
		staticFiles.expireTime(600L);

		// start game for dummy player
		engine.startGame(0,0);

		// allow requests from every domain
		options("/*",
				(request, response) -> {

					String accessControlRequestHeaders = request
							.headers("Access-Control-Request-Headers");
					if (accessControlRequestHeaders != null) {
						response.header("Access-Control-Allow-Headers",
								accessControlRequestHeaders);
					}

					String accessControlRequestMethod = request
							.headers("Access-Control-Request-Method");
					if (accessControlRequestMethod != null) {
						response.header("Access-Control-Allow-Methods",
								accessControlRequestMethod);
					}

					return "OK";
				});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.type("application/json");
		});

		get(Path.Api.WORLD, SpaceWorldController.world, json());

		get(Path.Api.SPACE, SpaceWorldController.space, json());

		get(Path.Api.DOUBLE_SPACE, SpaceWorldController.doublespace, json());

		get(Path.Api.TRIPPLE_SPACE, SpaceWorldController.tripplespace, json());

		get(Path.Api.STEP, SpaceWorldController.step, json());


		get(Path.Api.START, SpaceWorldController.start, json());

		get(Path.Api.STOP, SpaceWorldController.stop, json());


		after("*", Filters.addGzipHeader);
	}
}
