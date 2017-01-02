package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;
import com.space.server.engine.api.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represent a simple space world. A world consist of an ordered sequence of world segments.
 *
 * Created by superernie77 on 04.12.2016.
 */
public class SimpleWorldImpl implements SpaceWorld {

    private List<Segment> segments  = new ArrayList<>(10);

    private List<WorldEvent> events = new ArrayList<>();

    private Integer worldId;

    private Integer startSegment = 0;

    private Integer startStep = 0;

    @Override
    public Integer getStartSegment() {
        return startSegment;
    }

    @Override
    public Integer getStartStep() {
        return startStep;
    }

    @Override
    public void setStartSegment(Integer no) {
        startSegment = no;
    }

    @Override
    public void setStartStep(Integer no) {
        startStep = no;
    }

    @Override
    public Integer getWorldId() {
        return worldId;
    }

    @Override
    public void setWorldId(Integer id) {
        worldId = id;
    }

    @Override
    public Segment getSegment(int no) {
        return segments.get(no);
    }

    @Override
    public void setSegment(Segment seg, int no){
        segments.set(no, seg);
    }

    @Override
    public SpaceWorld addSegment(Segment seg){
        segments.add(seg);
        return this;
    }

    @Override
    public void addEvent(WorldEvent event) {
        events.add(event);
    }

    @Override
    public List<WorldEvent> getEventsForPlayer(int playerId) {
        return events.stream().filter( e -> e.getPlayerId() == playerId).collect(Collectors.toList());
    }
}
