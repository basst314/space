package com.space.server.domain.api;

import java.util.List;

/**
 * Interface for a regular space player.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpacePlayer extends Overlay{

    /**
     * Returns the unique player id.
     * @return
     */
    Integer getPlayerId();

    /**
     * Sets the player id.
     * @param id
     */
    void setPlayerId(Integer id);

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

    /**
     * Returns the active item
     * @return
     */
    Item getActiveItem();

    /**
     * Sets one of the items from the inventory to the active item.
     * @param no
     */
    void setActiveItem(int no);

    /**
     * Adds an item to the inventory.
     * @param item
     */
    void addItem(Item item);

}


