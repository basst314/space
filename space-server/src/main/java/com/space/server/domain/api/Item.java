package com.space.server.domain.api;

/**
 * Item owned by a player.
 * Created by superernie77 on 04.12.2016.
 */
public interface Item extends Overlay{

    /**
     * Return the character the item is displayed with when
     * it is in a players inventory.
     * @return String symbol of the item
     */
    String getItemSymbol();

}
