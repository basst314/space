package com.space.server.web.controller;

import com.space.server.core.World;
import com.space.server.web.SpaceStarterWeb;
import spark.Route;

import java.util.stream.Stream;

/**
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class SpaceWorldController {

	public static Route world = (request, response) -> {
		StringBuilder builder = new StringBuilder();
		builder.append(SpaceStarterWeb.spaceWorldProvider.getHero());
		builder.append(SpaceStarterWeb.spaceWorldProvider.getWorldEntry());
		Stream.generate(SpaceStarterWeb.spaceWorldProvider::getWorldContent).limit(100).forEach(builder::append);
		return new World(builder.toString());
	};
}
