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

    /**
     * Returns the direction the player is moving
     * @return
     */
    Direction getDirection();

    /**
     * sets the direction the player is moving
     * @param direction
     */
    void setDirection(Direction direction);

    /**
     * If the player has been moved in the current time step
     * @return
     */
    boolean isMoved();

    /**
     * ets if the player has been moved or not
     * @param moved
     */
    void setMoved(boolean moved);

    Step getActiveStep();

    void setActiveStep(Step step);



}


