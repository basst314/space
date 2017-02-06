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
                if (!isMonsterOnStep(step.next())){
                    step.getOverlays().remove(p);
                    step.next().addOverlay(p);
                    p.setActiveStep(step.next());
                }
            }
        } else if (p.getDirection().equals(Direction.BACKWARD)) {
            if (step.previous() != null) {
                if (!isMonsterOnStep(step.previous())) {
                    step.getOverlays().remove(p);
                    step.previous().addOverlay(p);
                    p.setActiveStep(step.previous());
                }
            }
        }
        p.setMoved(true);
    }

    public void monsterCombat(Step current, SpacePlayer player){
        // Monster hit from behind
        Step previous = current.previous();
        if (previous != null){
            previous.getOverlays().stream().filter( m -> m instanceof Monster).map( p -> (Monster)p).forEach( m -> player.getHealth().processHit());
        }


        // Monster hit from front
        Step next = current.next();
        if (next != null){
            next.getOverlays().stream().filter( m -> m instanceof Monster).map( p -> (Monster)p).forEach( m -> player.getHealth().processHit());
        }

    }

    private boolean isMonsterOnStep(Step step){
        return step.getOverlays().stream().anyMatch(  m -> m instanceof  Monster );
    }

    /**
     * Creates a SimpleSpaceWorld-Objekt from a string.
     * Resulting world string will have health values displayed in addition to the input string
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

        return world;
    }
}
