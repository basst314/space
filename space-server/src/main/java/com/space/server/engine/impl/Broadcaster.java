package com.space.server.engine.impl;

import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import org.eclipse.jetty.websocket.api.Session;

import static com.space.server.engine.api.WorldEventType.UPDATE;

/**
 * Created by superernie77 on 02.02.2017.
 */
public class Broadcaster {

    private int worldId;

    private int playerId;

    private Session session;

    private GameEngine engine;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

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
