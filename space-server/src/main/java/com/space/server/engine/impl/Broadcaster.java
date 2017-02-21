package com.space.server.engine.impl;

import com.space.server.core.World;
import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpacePlayer;
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
 * Encapsulates all data and the logic to broadcast to a space websocket client.
 * Created by superernie77 on 02.02.2017.
 */
public class Broadcaster {

    private static final Logger LOG = LoggerFactory.getLogger(Broadcaster.class);

    private int worldId;

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

    public WorldEvent createWorldEvent(Integer playerId){
        SpaceWorld world = engine.getWorld(this.getWorldId());
        if (world.getSegments().stream().anyMatch( s -> s.containsPlayer(playerId))){
            Segment segmentwithplayer = world.getSegments().stream().filter( s -> s.containsPlayer(playerId)).findFirst().get();

            World gameWorld = new World(segmentwithplayer.getContent());

            SpacePlayer player =  engine.getPlayer(playerId);

            WorldEvent resultEvent = new WorldEventImpl();
            resultEvent.setWorldId(this.getWorldId());
            resultEvent.setPlayerId(playerId);
            resultEvent.setType(UPDATE);
            resultEvent.setWorld(gameWorld);
            resultEvent.setInventory(player.getInventory());
            return resultEvent;
        }
        return null;
    }

    public void broadcast(Session playerSession, WorldEvent resultEvent) {
        try {
            String result = JsonUtil.toJson(resultEvent);
            playerSession.getRemote().sendString(result);
            LOG.debug(result);
        } catch (IOException ex){
            LOG.error("Error broadcasting!");
        }

    }
}
