package com.space.server.web.controller;

import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.engine.impl.WorldEventImpl;
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

	public static Route space = (request, response) -> {
		WorldEvent space = new WorldEventImpl();
		space.setType(WorldEventType.SPACE);
		space.setPlayerId(0);
		space.setWorldId(0);
		SpaceStarterWeb.engine.getWorld(0).addEvent(space);
		return new World("");
	};

	public static Route doublespace = (request, response) -> {
		WorldEvent space = new WorldEventImpl();
		space.setType(WorldEventType.DOUBLE_SPACE);
		space.setPlayerId(0);
		space.setWorldId(0);
		SpaceStarterWeb.engine.getWorld(0).addEvent(space);
		return new World("");
	};

	public static Route tripplespace = (request, response) -> {
		WorldEvent space = new WorldEventImpl();
		space.setType(WorldEventType.TRIPPLE_SPACE);
		space.setPlayerId(0);
		space.setWorldId(0);
		SpaceStarterWeb.engine.getWorld(0).addEvent(space);
		return new World("");
	};

	public static Route step = (request, response) -> {
		SpaceStarterWeb.engine.stepWorld(0);
		return new World("");
	};


}
