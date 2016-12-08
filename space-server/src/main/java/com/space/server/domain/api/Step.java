package com.space.server.domain.api;

/**
 ~ Interface for a single step in a game world segment.
 * Created by superernie77 on 04.12.2016.
 */
public interface Step {

    /**
     * Returns the current content of the step as a string.
     * @return
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

    void setNext(Step next);

    void setPrevious(Step previous);
}
