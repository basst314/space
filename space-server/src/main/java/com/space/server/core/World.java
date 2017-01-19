package com.space.server.core;

/**
 * Simple POJO to transport  world data to the client
 * Created by Markus Oppeneiger on 20.10.2016.
 */
public class World {

	private final String world;

	public World(String world) {
		this.world = world;
	}

	/**
	 * Returns the string representation of the world
	 * @return e.g. "H....M..W"
	 */
	public String getWorld() {
		return world;
	}
}
