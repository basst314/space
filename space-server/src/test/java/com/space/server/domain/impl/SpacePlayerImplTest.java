package com.space.server.domain.impl;
import static org.mockito.Mockito.*;

import com.space.server.domain.api.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertEquals(player.getContent(),"H");
    }

    @Test
    public void testSetActiveItem(){
        Item item = mock(Item.class);
        when(item.getItemSymbol()).thenReturn("/");

        player.addItem(item);

        player.setActiveItem(0);

        String content = player.getContent();

        Assert.assertEquals(content, "H/");
    }
}
