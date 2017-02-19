package com.space.server.engine.impl;

import com.space.server.core.World;
import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.GameEngine;
import com.space.server.engine.api.WorldEvent;
import com.space.server.web.util.JsonUtil;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

import static com.space.server.engine.api.WorldEventType.UPDATE;

/**
 * Created by superernie77 on 02.02.2017.
 */
public class Broadcaster {

    private static final Logger LOG = LoggerFactory.getLogger(Broadcaster.class);

    private int worldId;

    private int playerId;

    private GameEngine engine;

    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    public int getWorldId() { return worldId; }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public WorldEvent createWorldEvent(){
        SpaceWorld world = engine.getWorld(this.getWorldId());
        Segment segmentwithplayer = world.getSegments().stream().filter( s -> s.containsPlayer(playerId)).findFirst().get();
        World gameWorld = new World(segmentwithplayer.getContent());

        WorldEvent resultEvent = new WorldEventImpl();
        resultEvent.setWorldId(this.getWorldId());
        resultEvent.setType(UPDATE);
        resultEvent.setWorld(gameWorld);

        return resultEvent;
    }

    public void broadcast(Session playerSession, WorldEvent resultEvent) throws IOException {
        String result = JsonUtil.toJson(resultEvent);
        playerSession.getRemote().sendString(result);
        LOG.debug(result);
    }
}
