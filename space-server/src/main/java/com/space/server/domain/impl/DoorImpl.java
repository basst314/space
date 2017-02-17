package com.space.server.domain.impl;

import com.space.server.domain.api.Door;
import com.space.server.domain.api.Overlay;
import com.space.server.domain.api.Step;

/**
 * Created by superernie77 on 16.02.2017.
 */
public class DoorImpl implements Door {

    private String content = "D";

    private Step targetStep;

    private Step sourceStep;

    public DoorImpl(Step source, Step target ){
        targetStep = target;
        sourceStep = source;
    }

    @Override
    public Step getTargetStep() {
        return targetStep;
    }

    @Override
    public Step getSourceStep() {
        return sourceStep;
    }

    @Override
    public String getContent() {
        return content;
    }

}
