package com.space.server.domain.api;

import java.util.List;

/**
 ~ Interface for a single step in a game world segment.
 * Created by superernie77 on 04.12.2016.
 */
public interface Step {

    /**
     * Returns the current content of the step as a string.
     * @return String symbol
     */
    String getContent();

    /**
     * Sets the string representation of the step
     * @param character
     */
    void setContent(String character);

    /**
     * True, if this step connect two segments
     * @return
     */
    boolean isConnector();

    /**
     * Retruns the next step of the segment.
     * @return
     */
    Step next();

    /**
     * Retruns the next previous of the segment.
     * @return
     */
    Step previous();

    /**
     * Sets the next step in the segment.
     * @param next
     */
    void setNext(Step next);


    /**
     * Sets the previous step in the segment.
     * @param previous
     */
    void setPrevious(Step previous);

    /**
     * Adds an overlay to the step
     * @param over
     */
    void addOverlay(Overlay over);

    List<Overlay> getOverlays();

    /**
     * Checks if a player currently uses this step
     * @return
     */
    boolean isPlayerPresent();

    /**
     * Returns the players the stand inside this step
     */
    List<SpacePlayer> getPlayers();
}
