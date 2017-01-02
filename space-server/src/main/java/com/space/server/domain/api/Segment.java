package com.space.server.domain.api;

import java.util.List;

/**
 * Consecutive sequence of steps
 * Created by superernie77 on 04.12.2016.
 */
public interface Segment {

    /**
     * Returns the content of the segment as a string.
     * @return the content string
     */
    String getContent();

    /**
     * Adds a new step to the end of the segment.
     * @param step the new step
     * @return reference of the segment for chaining of methods
     */
    Segment addStep(Step step);

    /**
     * Returns the step with given number.
     * @param no step number
     * @return the step
     */
    Step getStep(int no);

}
