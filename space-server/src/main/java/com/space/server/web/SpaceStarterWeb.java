package com.space.server.web;

import com.space.server.core.SpaceWorldProvider;
import com.space.server.core.SpaceWorldProviderImpl;
import com.space.server.web.controller.SpaceWorldController;
import com.space.server.web.util.Filters;
import com.space.server.web.util.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.space.server.web.util.JsonUtil.json;
import static spark.Spark.*;

/**
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class SpaceStarterWeb {

	private static final Logger LOG = LoggerFactory.getLogger(SpaceStarterWeb.class);

	public static SpaceWorldProvider spaceWorldProvider = new SpaceWorldProviderImpl();

	public static void main(String[] args) {
		int port = 8080;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		port(port);
		LOG.info("Server running on port {}", port);

		staticFiles.location("/static");
		staticFiles.expireTime(600L);

		get(Path.Api.WORLD, SpaceWorldController.world, json());

		after("*", Filters.addGzipHeader);
	}
}
