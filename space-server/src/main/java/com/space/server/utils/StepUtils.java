package com.space.server.utils;

import com.space.server.domain.api.Overlay;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.Step;

/**
 * Utility methods for basic step operations.
 * Created by superernie77 on 16.12.2016.
 */
public class StepUtils {

    public void movePlayerOneStepForeward(Step step){
        SpacePlayer p = null;
        for (Overlay o : step.getOverlays()){
            if (o instanceof SpacePlayer) {
                p = (SpacePlayer)o;
            }
        }
        if (step.next() != null) {
            step.getOverlays().remove(p);
            step.next().addOverlay(p);
        }
    }
}
