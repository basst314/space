package com.space.server.domain.items.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test a sword.
 * Created by superernie77 on 12.12.2016.
 */
public class SwordTest {

    private Sword sword;

    @Before
    public void setup(){
        sword = new Sword();
    }

    @Test
    public void getContent() throws Exception {
        Assert.assertTrue(sword.getContent().equals("W"));
    }
}