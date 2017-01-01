package com.space.server.web.controller;

import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.web.SpaceStarterWeb;
import spark.Route;

import java.util.stream.Stream;

/**
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class SpaceWorldController {

	public static Route world = (request, response) -> {
		SpaceWorld world = SpaceStarterWeb.engine.getWorld(0);
		return new World(world.getSegment(0).getContent());
	};
}
