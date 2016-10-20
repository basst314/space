package com.space.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceStarter {

	public static final Logger LOG = LoggerFactory.getLogger(SpaceStarter.class);

	public static void main(String[] args) {
		SpaceWorldProvider world = new SpaceFantasyWorld();
		
		LOG.debug(world.getHero());
		
		for (int i = 0 ; i < 100 ; i++) {
			LOG.debug(world.getWorldConent());
		}
	}
}
