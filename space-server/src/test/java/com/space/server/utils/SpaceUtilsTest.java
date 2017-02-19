package com.space.server.utils;

import com.space.server.domain.api.*;
import com.space.server.domain.impl.MonsterImpl;
import com.space.server.domain.impl.StepImpl;
import com.space.server.domain.impl.SpacePlayerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 30.01.2017.
 */
public class SpaceUtilsTest {

    private SpaceUtils utils;

    private Step current;

    private Step next;

    private Step previous;

    private SpacePlayer player;

    @Before
    public void setup(){
        utils = new SpaceUtils();

        current = new StepImpl();
        previous = new StepImpl();
        next = new StepImpl();

        player = new SpacePlayerImpl();
        player.setDirection(Direction.FORWARD);
        current.addOverlay(player);

        current.setNext(next);
        current.setPrevious(previous);
    }

    @Test
    public void testMovePlayerOneStepForward(){

        player.setDirection(Direction.FORWARD);

        utils.movePlayerOneStep(current);

        // player is on next step
        Assert.assertEquals(player, current.next().getOverlays().get(0));
    }

    @Test
    public void testMovePlayerOneStepBackwardsBlockedByMonster(){

        player.setDirection(Direction.BACKWARD);
        previous.addOverlay(new MonsterImpl());

        utils.movePlayerOneStep(current);

        // Monster blocks previous Step. Player still on current step.
        Assert.assertEquals(player, current.getOverlays().get(0));
    }

    @Test
    public void testMovePlayerOneStepBlockedByMonster(){

        player.setDirection(Direction.FORWARD);
        next.addOverlay(new MonsterImpl());

        utils.movePlayerOneStep(current);

        // Monster blocks next Step. Player still on current step.
        Assert.assertEquals(player, current.getOverlays().get(0));

    }

    @Test
    public void testMovePlayerOneStepBackwards(){

        player.setDirection(Direction.BACKWARD);

        utils.movePlayerOneStep(current);

        // player is on previous step
        Assert.assertEquals(player, current.previous().getOverlays().get(0));
    }

    @Test
    public void testMonsterCombatNextStep(){

        player.setDirection(Direction.FORWARD);
        next.addOverlay(new MonsterImpl());

        utils.monsterCombat(current,player);

        // Player has one hitpoint less
        Assert.assertTrue(player.getHealth().getContent().equals("²"));
    }

    @Test
    public void testMonsterCombatKillHero(){

        player.setDirection(Direction.FORWARD);
        next.addOverlay(new MonsterImpl());

        // hero hit 3 times
        utils.monsterCombat(current,player);
        utils.monsterCombat(current,player);
        utils.monsterCombat(current,player);

        // Player is dead
        Assert.assertTrue(player.getHealth().isDead());
    }

    @Test
    public void testMonsterCombatPreviousStep(){

        player.setDirection(Direction.FORWARD);
        previous.addOverlay(new MonsterImpl());

        utils.monsterCombat(current,player);

        // Player has one hitpoint less
        Assert.assertTrue(player.getHealth().getContent().equals("²"));
    }

    @Test
    public void testCreateSegmentFromString(){

        Segment segment = utils.createSegmentFromString("....MMM");

        Assert.assertTrue(segment.getContent().equals("....M¹M¹M¹"));
    }

    @Test
    public void testCreateWorld(){

        SpaceWorld world = utils.createWorldWithSingleSegment("....MMM");

        Assert.assertTrue(world.getSegment(0).getContent().equals("....M¹M¹M¹"));
    }
}