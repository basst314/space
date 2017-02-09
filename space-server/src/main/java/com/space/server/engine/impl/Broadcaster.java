package com.space.server.engine.impl;

import com.space.server.engine.api.GameEngine;

/**
 * Created by superernie77 on 02.02.2017.
 */
public class Broadcaster {

    private int worldId;

    private int playerId;

    private GameEngine engine;

    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    public int getWorldId() {

        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}