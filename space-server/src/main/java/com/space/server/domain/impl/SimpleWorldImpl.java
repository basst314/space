package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.SpaceWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represent a simple space world. A world consist of an ordered sequence of world segments.
 *
 * Created by superernie77 on 04.12.2016.
 */
public class SimpleWorldImpl implements SpaceWorld {

    private List<Segment> segments  = new ArrayList<>(10);

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
}
