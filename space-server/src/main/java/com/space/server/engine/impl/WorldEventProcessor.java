package com.space.server.engine.impl;

import com.space.server.domain.api.Direction;
import com.space.server.domain.api.Item;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventType;

import java.util.List;

/**
 * Processes WorldEvents
 * Created by superernie77 on 27.12.2016.
 */
public class WorldEventProcessor {

    /**
     * Processes all given events for one player
     * @param events
     * @param player
     */
    public void processEvents(List<WorldEvent> events, SpacePlayer player){

        for(WorldEvent event : events){
            if (event.getType().equals(WorldEventType.DOUBLE_SPACE)) {
                Direction dir = player.getDirection();
                if (dir == Direction.FORWARD){
                    player.setDirection(Direction.BACKWARD);
                } else if (dir == Direction.BACKWARD) {
                    player.setDirection(Direction.FORWARD);
                }
            }

            if (event.getType().equals(WorldEventType.TRIPPLE_SPACE)) {

                Item active =  player.getActiveItem();
                List<Item> inv = player.getInventory();

                int i = inv.indexOf(active);

                if (++i < inv.size()){
                    player.setActiveItem(i);
                } else {
                    player.setActiveItem(0);
                }
            }
        }
    }
}
