package com.space.server.domain.api;

import com.space.server.domain.impl.Health;
import com.space.server.domain.items.api.ItemUsage;

import java.util.List;

/**
 * Interface for a regular space player.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpacePlayer extends Overlay, Blockable {

	/**
	 * Return the health object of the player
	 * @return
	 */
	Health getHealth();

    /**
     * Returns the unique player id.
	 * @return player id
	 */
	Integer getPlayerId();

    /**
     * Sets the player id.
	 * @param id the id to set
	 */
	void setPlayerId(Integer id);

    /**
     * Returns the current inventory of the player.
     * @return list of items
     */
    List<Item> getInventory();

    /**
     * Get a specific item from the inventory.
	 * @param no inventory index
	 * @return inventory item
	 */
	Item getItem(int no);

    /**
     * Returns the active item
	 * @return active item
	 */
	Item getActiveItem();

    /**
     * Sets one of the items from the inventory to the active item.
	 * @param no inventory index of item to set active
	 */
	void setActiveItem(int no);

    /**
     * Adds an item to the inventory.
	 * @param item item to add
	 * @return index of added item in inventory
	 */
	int addItem(Item item);

    /**
     * Returns the direction the player is moving
	 * @return active direction
	 */
	Direction getDirection();

    /**
     * sets the direction the player is moving
	 * @param direction direction to set
	 */
	void setDirection(Direction direction);

    /**
     * If the player has been moved in the current time step
	 * @return whether player was moved in this step
	 */
	boolean isMoved();

    /**
     * ets if the player has been moved or not
	 * @param moved set player moved
	 */
	void setMoved(boolean moved);

	/**
	 * Sets the step the hero stands on
	 * @return
	 */
    Step getActiveStep();

	/**
	 * set the step the hero stands on
	 * @param step
	 */
    void setActiveStep(Step step);

	/**
	 * resets all activities done for this player in an game-engine-cycle
	 */
	void resetActivities();

	/**
	 * set whether the active item has already been used in this step
	 *
	 * @param used flag active item used
	 */
	public void setActiveItemUsed(boolean used);

	public ItemUsage getActiveItemUsage();

	public void setActiveItemUsage(ItemUsage usage);

	public boolean isReadyToMove();
}


