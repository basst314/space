package com.space.server.domain.api;

import com.space.server.engine.api.WorldEvent;

/**
 * Interface for a space game world.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpaceWorld {

    Integer getWorldId();

    void setWorldId(Integer id);

    /**
     * returns a segment
     * @param no
     * @return
     */
    Segment getSegment(int no);

    /**
     * resets a segment with a new one
     * @param seg
     * @param no
     */
    void setSegment(Segment seg, int no);

    /**
     * Adds a segment to the world
     * @param seg
     * @return
     */
    SpaceWorld addSegment(Segment seg);

    void addEvent(WorldEvent event);
}
