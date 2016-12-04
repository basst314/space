package com.space.server.domain.api;

import java.util.List;

/**
 * Interface for a regular space player.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpacePlayer {

    /**
     * Returns the current inventory of the player.
     * @return list of items
     */
    List<Item> getInventory();

    /**
     * Get a specific item from the inventory.
     * @param no
     * @return
     */
    Item getItem(int no);

}


