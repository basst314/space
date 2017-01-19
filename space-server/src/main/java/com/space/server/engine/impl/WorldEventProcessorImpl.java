package com.space.server.engine.impl;

import com.space.server.domain.api.*;
import com.space.server.engine.api.WorldEvent;
import com.space.server.engine.api.WorldEventProcessor;
import com.space.server.engine.api.WorldEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Processes WorldEvents
 * Created by superernie77 on 27.12.2016.
 */
@Service
class WorldEventProcessorImpl implements WorldEventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(WorldEventProcessorImpl.class);

    /**
     * Processes all given events for one player
	 * @param events events to process
	 * @param player player to process
	 */
	public void processEvents(List<WorldEvent> events, SpacePlayer player){

        LOG.debug("Processing events for playerId {}", player.getPlayerId());

        for(WorldEvent event : events){
            if (event.getType().equals(WorldEventType.DOUBLE_SPACE)) {

                LOG.debug("Processing DOUBLE_SPACE event.");

                Direction dir = player.getDirection();
                if (dir == Direction.FORWARD){
                    player.setDirection(Direction.BACKWARD);
                } else if (dir == Direction.BACKWARD) {
                    player.setDirection(Direction.FORWARD);
                }
            } else if (event.getType().equals(WorldEventType.TRIPPLE_SPACE)) {

                LOG.debug("Processing TRIPPLE_SPACE event.");

                Item active =  player.getActiveItem();
                List<Item> inv = player.getInventory();

                int i = inv.indexOf(active);

                if (++i < inv.size()){
                    player.setActiveItem(i);
                } else {
                    player.setActiveItem(0);
                }
            } else if (event.getType().equals(WorldEventType.SPACE)) {

                LOG.debug("Processing SPACE event.");
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

                            LOG.debug("Item collected for playerId {}", player.getPlayerId());

                            player.setActiveItem(inventoryIndex);
                            // pause player movement for one step
                            player.setMoved(true);
                        }
                        break;
                    } else if (overlay instanceof Monster) {


                        Item activeItem = player.getActiveItem();
						if (activeItem instanceof Weapon) {
							LOG.debug("Monster killed for playerId {}", player.getPlayerId());

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
