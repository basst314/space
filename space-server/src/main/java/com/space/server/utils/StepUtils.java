package com.space.server.utils;

import com.space.server.domain.api.*;
import com.space.server.domain.impl.BasicMonster;
import com.space.server.domain.impl.BasicStep;
import com.space.server.domain.impl.SimpleSegment;
import com.space.server.domain.impl.SimpleWorldImpl;
import com.space.server.domain.items.impl.Sword;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Utility methods for basic step operations.
 * Created by superernie77 on 16.12.2016.
 */
@Service
public class StepUtils {

    /**
     * Moves a player one step in the direction of its move attribute
     *
     * @param step the step the player currently stands on
     */
    public void movePlayerOneStep(Step step) {
        SpacePlayer p = null;
        for (Overlay o : step.getOverlays()) {
            if (o instanceof SpacePlayer) {
                p = (SpacePlayer) o;
            }
        }

        // if there is no player or it is not ready to move, nothing to do
        if (p == null || !p.isReadyToMove()) {
            return;
        }

        // move player in defined direction
        if (p.getDirection().equals(Direction.FORWARD)) {
            if (step.next() != null) {
                step.getOverlays().remove(p);
                step.next().addOverlay(p);
                p.setActiveStep(step.next());
            }
        } else if (p.getDirection().equals(Direction.BACKWARD)) {
            if (step.previous() != null) {
                step.getOverlays().remove(p);
                step.previous().addOverlay(p);
                p.setActiveStep(step.previous());
            }
        }
        p.setMoved(true);
    }

    /**
     * Creates a SimpleSpaceWorld-Objekt from a string.
     * @param worldString the world string e.g. "H....W..M"
     * @return objekt representation of the string
     */
    public SpaceWorld createWorldFromString(String worldString) {
        SimpleWorldImpl world = new SimpleWorldImpl();
        world.setWorldId(0);

        SimpleSegment segment = new SimpleSegment();

        world.addSegment(segment);

        String[] chars = worldString.split("");

        List<String> worldChars = Arrays.asList(chars);

        for (String tocken : worldChars) {
            BasicStep step = new BasicStep();
            Overlay over = null;

            if (tocken.equals("W")) {
                over = new Sword();
            } else if (tocken.equals("M")) {
                over = new BasicMonster();
            }
            if (over != null) {
                step.addOverlay(over);
            }
            segment.addStep(step);
        }

        // just to make sure the world was setup properly
        assert world.getSegment(0).getContent().equals(worldString);

        return world;
    }
}
