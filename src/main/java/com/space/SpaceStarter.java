package com.space;

public class SpaceStarter {
	
	public static void main(String[] args) {
		
		
		SpaceWorldProvider world = new SpaceFantasyWorld();
		
		
		System.out.print(world.getHero());
		
		for (int i = 0 ; i < 100 ; i++) {
			System.out.print(world.getWorldConent());
		}
		
	}

}
