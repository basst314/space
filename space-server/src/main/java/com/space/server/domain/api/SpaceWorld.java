package com.space.server.domain.api;

/**
 * Interface for a space game world.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpaceWorld {

    Segment getSegment(int no);

    void setSegment(Segment seg, int no);

}
