package com.space.server.domain.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by superernie77 on 26.01.2017.
 */
public class BasicMonsterTest {

    private BasicMonster monster;

    @Before
    public void setup(){
        monster = new BasicMonster();
    }

    @Test
    public void testGetHealth(){
        Assert.assertNotNull(monster.getHealth().getContent());
    }
}
