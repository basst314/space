package com.space.server.domain.impl;

import com.space.server.domain.api.Segment;
import com.space.server.domain.api.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A world segement is a consecutive sequence of steps.
 * Segments can be connected to other segments.
 * Created by superernie77 on 04.12.2016.
 */
public class SimpleSegment implements Segment {

    List<Step> steps = new ArrayList<>(0);

    @Override
    public String getContent() {
        String result = steps.stream().map(s -> s.getContent()).collect(Collectors.joining());
        return result;
    }

    @Override
    public Segment addStep(Step step){
        if (steps.size() > 0) {
            Step previous = steps.get(steps.size() - 1);
            step.setPrevious(previous);
            previous.setNext(step);
        }
        steps.add(step);
        return this;
    }

    @Override
    public Step getStep(int no) {
        return steps.get(no);
    }

    @Override
    public List<Step> getAllSteps(){
        return steps;
    }


}
