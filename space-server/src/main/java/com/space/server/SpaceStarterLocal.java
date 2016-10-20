package com.space.server;

import com.space.server.core.SpaceWorldProviderImpl;
import com.space.server.core.SpaceWorldProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceStarterLocal {

	public static final Logger LOG = LoggerFactory.getLogger(SpaceStarterLocal.class);

	public static void main(String[] args) {
		SpaceWorldProvider world = new SpaceWorldProviderImpl();
		
		LOG.debug(world.getHero());
		
		for (int i = 0 ; i < 100 ; i++) {
			LOG.debug(world.getWorldContent());
		}
	}
}
