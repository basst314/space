package com.space.server;

import com.space.server.SpaceFantasyWorld;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Markus on 20.10.2016.
 */
public class SpaceFantasyWorldTests {

	private SpaceFantasyWorld spaceFantasyWorld = new SpaceFantasyWorld();

	@Test
	public void getHero()
	{
		assertThat(spaceFantasyWorld.getHero()).isEqualTo("H");
	}
}
