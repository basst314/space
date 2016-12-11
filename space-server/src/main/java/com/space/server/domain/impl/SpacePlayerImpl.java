package com.space.server.domain.impl;

import com.space.server.domain.api.Item;
import com.space.server.domain.api.Overlay;
import com.space.server.domain.api.SpacePlayer;

import java.util.List;

/**
 * Implementation of a space player.
 * Created by superernie77 on 11.12.2016.
 */
public class SpacePlayerImpl implements SpacePlayer{

    private Integer playerId;

    private String content = "H";

    private List<Item> inventory;

    @Override
    public Integer getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(Integer id) {
        playerId = id;
    }

    @Override
    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public Item getItem(int no) {
        return inventory.get(no);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String character) {
        content = character;
    }
}