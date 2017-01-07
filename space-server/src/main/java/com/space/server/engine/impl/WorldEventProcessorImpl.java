package com.space.server.engine.impl;

import com.space.server.domain.api.*;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventProcessor;
import com.space.server.engine.api.WorldEventType;

import java.util.List;

/**
 * Processes WorldEvents
 * Created by superernie77 on 27.12.2016.
 */
public class WorldEventProcessorImpl implements WorldEventProcessor{

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
            } else if (event.getType().equals(WorldEventType.TRIPPLE_SPACE)) {

                Item active =  player.getActiveItem();
                List<Item> inv = player.getInventory();

                int i = inv.indexOf(active);

                if (++i < inv.size()){
                    player.setActiveItem(i);
                } else {
                    player.setActiveItem(0);
                }
            } else if (event.getType().equals(WorldEventType.SPACE)) {
                // use active item
                if (player.getActiveItem() != null) {
                    player.setActiveItemUsed(true);
                }

                Direction dir = player.getDirection();
                Step nextStep = null;
                if (dir == Direction.FORWARD){
                    nextStep = player.getActiveStep().next();

                } else if (dir == Direction.BACKWARD) {
                    nextStep = player.getActiveStep().previous();
                }

                List<Overlay> overlays = nextStep.getOverlays();

                Overlay toRemove = null;
                for (Overlay overlay : overlays){

                    if (overlay instanceof Item){
                        int inventoryIndex = player.addItem((Item) overlay);
                        toRemove = overlay;
                        if (player.getActiveItem() == null){
                            player.setActiveItem(inventoryIndex);
                            // pause player movement for one step
                            player.setMoved(true);
                        }
                        break;
                    } else if (overlay instanceof Monster) {
                        Item activeItem = player.getActiveItem();
                        if (activeItem instanceof Weapon){
                            toRemove = overlay;
                            break;
                        }
                    }
                }
                if (toRemove != null){
                    overlays.remove(toRemove);
                }
            }
        }
    }
}
