package com.space.server.core;

public interface SpaceWorldProvider {
	
	String getWorldContent();
	
	String interact();
	
	// Gibt den Hero zurück.
	String getHero();

}
