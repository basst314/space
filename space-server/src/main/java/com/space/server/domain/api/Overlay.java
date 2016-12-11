package com.space.server.domain.api;

/**
 * An object that can be placed on top of a step.
 * Created by superernie77 on 11.12.2016.
 */
public interface Overlay {

    /**
     * Returns the current content of the overly as a string.
     * @return
     */
    String getContent();

    /**
     * Sets the string representation of the overlay
     * @param character
     */
    void setContent(String character);
}
