package com.space.server;

import com.space.server.core.SpaceWorldProviderImpl;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Markus on 20.10.2016.
 */
public class SpaceFantasyWorldTests {

	private SpaceWorldProviderImpl spaceWorldProviderImpl = new SpaceWorldProviderImpl();

	@Test
	public void getHero()
	{
		assertThat(spaceWorldProviderImpl.getHero()).isEqualTo("H");
	}
}
