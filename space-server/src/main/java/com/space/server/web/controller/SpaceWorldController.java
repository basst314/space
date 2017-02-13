package com.space.server.web.controller;

import com.space.server.core.World;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;
import com.space.server.engine.impl.WorldEventImpl;
import com.space.server.web.SpaceStarterWeb;
import spark.Route;

/**
 * Actions for REST integration. All operation are executed against playerId == 0 and worldId == 0
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class SpaceWorldController {

	public static Route space = (request, response) -> {
		WorldEvent space = new WorldEventImpl();
		space.setType(WorldEventType.SPACE);
		space.setPlayerId(0);
		space.setWorldId(0);
		SpaceWorld world = SpaceStarterWeb.engine.getWorld(0);
		world.addEvent(space);
		return new World(world.getSegment(0).getContent());
	};

	public static Route doublespace = (request, response) -> {
		WorldEvent space = new WorldEventImpl();
		space.setType(WorldEventType.DOUBLE_SPACE);
		space.setPlayerId(0);
		space.setWorldId(0);

		SpaceWorld world = SpaceStarterWeb.engine.getWorld(0);
		world.addEvent(space);
		return new World(world.getSegment(0).getContent());
	};

	public static Route tripplespace = (request, response) -> {
		WorldEvent space = new WorldEventImpl();
		space.setType(WorldEventType.TRIPPLE_SPACE);
		space.setPlayerId(0);
		space.setWorldId(0);
		SpaceWorld world = SpaceStarterWeb.engine.getWorld(0);
		world.addEvent(space);
		return new World(world.getSegment(0).getContent());
	};

	public static Route step = (request, response) -> {
		SpaceStarterWeb.engine.stepWorld(0);
		SpaceWorld world = SpaceStarterWeb.engine.getWorld(0);
		return new World(world.getSegment(0).getContent());
	};

	public static Route start = (request, response) -> {
		SpaceStarterWeb.engine.startGame(0,0);
		SpaceWorld world = SpaceStarterWeb.engine.getWorld(0);
		return new World(world.getSegment(0).getContent());
	};

	public static Route stop = (request, response) -> {
		SpaceStarterWeb.engine.stopGame(0,0);

		return "world stopped";
	};
}
