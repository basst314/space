package com.space.server.domain.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the health object
 * Created by superernie77 on 26.01.2017.
 */
public class HealthTest {

    private Health health;

    @Before
    public void setup(){
        health = new Health();
    }

    @Test
    public void testGetContent(){
        health.setHealth(1);
        Assert.assertEquals("¹",health.getContent());

        health.setHealth(2);
        Assert.assertEquals("²",health.getContent());

        health.setHealth(3);
        Assert.assertEquals("³",health.getContent());
    }

    @Test
    public void testIsDead(){
        health.setHealth(0);

        Assert.assertTrue(health.isDead());
    }

    @Test
    public void testHit(){
        health.setHealth(3);

        health.processHit();
        Assert.assertEquals("²",health.getContent());

    }

    @Test
    public void testHeal(){
        health.setHealth(1);

        health.processHeal();
        Assert.assertEquals("²",health.getContent());

    }
}
