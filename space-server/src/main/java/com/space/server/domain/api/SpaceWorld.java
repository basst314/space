package com.space.server.domain.api;

import com.space.server.engine.api.WorldEvent;

/**
 * Interface for a space game world.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpaceWorld {

    //TODO start segment no + step no

    /**
     * return the id of the world.
     * @return
     */
    Integer getWorldId();

    /**
     * Sets the unique world id.
     * @param id
     */
    void setWorldId(Integer id);

    /**
     * returns a segment
     * @param no
     * @return
     */
    Segment getSegment(int no);

    /**
     * replaces the given segment
     * @param seg
     * @param no
     */
    void setSegment(Segment seg, int no);

    /**
     * Adds a segment to the world.
     * @param seg
     * @return
     */
    SpaceWorld addSegment(Segment seg);

    /**
     * Add an event to the world that will be processed in the next world time step.
     * @param event
     */
    void addEvent(WorldEvent event);
}
