package com.space.server.domain.api;

import com.space.server.engine.api.WorldEvent;

import java.util.Collection;
import java.util.List;

/**
 * Interface for a space game world.
 * Created by superernie77 on 01.12.2016.
 */
public interface SpaceWorld {

    /**
     * Returns the start segment
     * @return
     */
    Integer getStartSegment();

    /**
     * Returns the start step
     * @return
     */
    Integer getStartStep();

    /**
     * Index of the start segment of a player
     * @param no
     */
    void setStartSegment(Integer no);

    /**
     * Index of the start step for a player
     * @param no
     */
    void setStartStep(Integer no);

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
     * @param no the number of the segment
     * @return
     */
    Segment getSegment(int no);

    /**
     * replaces the given segment
     * @param seg new segment
     * @param no number to replace
     */
    void setSegment(Segment seg, int no);

    /**
     * Adds a segment to the world.
     * @param seg the segment to add
     * @return the world for chaining
     */
    SpaceWorld addSegment(Segment seg);

    /**
     * Add an event to the world that will be processed in the next world time step.
     * @param event the event to add
     */
    void addEvent(WorldEvent event);

    /**
     * Removes all events from the parameter
     * @param events
     */
    void removeEvents(Collection<WorldEvent> events);

    /**
     * Returns all event for a certain player in the world
     * @param playerId
     * @return
     */
    List<WorldEvent> getEventsForPlayer(int playerId);

}
