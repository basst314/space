package com.space.server.domain.impl;

import com.space.server.domain.api.Direction;
import com.space.server.domain.api.Item;
import com.space.server.domain.items.api.ItemUsage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test the player implementation
 * Created by superernie77 on 12.12.2016.
 */
public class SpacePlayerImplTest {

    private SpacePlayerImpl player;

    @Before
    public  void setup(){
        player = new SpacePlayerImpl();
    }

    @Test
    public void testGetContent(){
        Assert.assertEquals(player.getContent(),"³H");
    }

    @Test
    public void testGetHealth(){
        Assert.assertNotNull(player.getHealth().getContent());
    }

    @Test
    public void testSetActiveItem(){
        Item item = mock(Item.class);
        when(item.getItemSymbol(Direction.FORWARD, ItemUsage.STANDBY)).thenReturn("/");

        player.addItem(item);

        player.setActiveItem(0);

        String content = player.getContent();

        Assert.assertEquals(content, "³H/");
    }

    @Test
    public void testSetActiveItemBackwards() {
        Item item = mock(Item.class);
        when(item.getItemSymbol(Direction.BACKWARD, ItemUsage.STANDBY)).thenReturn("\\");

        player.setDirection(Direction.BACKWARD);

        player.addItem(item);

        player.setActiveItem(0);

        String content = player.getContent();

        Assert.assertEquals(content, "\\H³");
    }
}
