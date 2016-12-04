package com.space.server.domain.api;

/**
 * Consecutive sequence of steps
 * Created by superernie77 on 04.12.2016.
 */
public interface Segment {

    String getContent();

    void addStep(Step step);
}
