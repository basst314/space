package com.space.server;

import java.util.Arrays;
import java.util.List;

public class SpaceFantasyWorld implements SpaceWorldProvider {

	private static final double EVENT_PROP = 0.9;
	
	private static final List<String> EVENTS = Arrays.asList("R", "T", "M", "W");
	
	@Override
	public String getWorldConent() {
		double rand = Math.random();
		if (rand > EVENT_PROP){
			int eventNo = (int)(Math.random() * EVENTS.size());
			return EVENTS.get(eventNo);
		} else {
			return ".";
		}
	}

	@Override
	public String interact() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getHero(){
		return "H";
	}

}
