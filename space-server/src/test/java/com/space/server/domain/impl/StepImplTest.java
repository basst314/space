package com.space.server.domain.impl;

import static org.mockito.Mockito.*;

import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.Step;
import com.space.server.domain.items.impl.Sword;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 08.12.2016.
 */
public class StepImplTest {

    private Step step;

    @Before
    public void setup(){
        step = new StepImpl();
    }

    @Test
    public void testPrevious(){
        Step newStep = new StepImpl();

        step.setPrevious(newStep);

        Assert.assertEquals(newStep, step.previous());

    }

    @Test
    public void testNext(){
        Step newStep = new StepImpl();

        step.setNext(newStep);

        Assert.assertEquals(newStep, step.next());
    }

    @Test
    public void testContent(){

        // as default content contains a dot.
        Assert.assertEquals(step.getContent(),".");

        step.setContent("W");

        // now we have a weapon
        Assert.assertEquals(step.getContent(),"W");
    }

    @Test
    public void testContentWithHero(){

        SpacePlayer player = mock(SpacePlayer.class);

        when(player.getContent()).thenReturn("P");

        step.addOverlay(player);

        // player is on the step
        Assert.assertEquals(step.getContent(),"P");
    }

    @Test
    public void testContentWithHeroAndWeapon(){

        SpacePlayer player = mock(SpacePlayer.class);
        when(player.getContent()).thenReturn("P");

        Sword sword = mock(Sword.class);

        step.addOverlay(player);
        step.addOverlay(sword);

        // player is on the step. Sword is invisible
        Assert.assertEquals(step.getContent(),"P");
    }

    @Test
    public void testContentWithWeapon(){

        Sword sword = mock(Sword.class);
        when(sword.getContent()).thenReturn("W");
        step.addOverlay(sword);

        // sword is on the step
        Assert.assertEquals(step.getContent(),"W");
    }
}
