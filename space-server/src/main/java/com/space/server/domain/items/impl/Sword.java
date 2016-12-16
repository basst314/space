package com.space.server.domain.items.impl;

import com.space.server.domain.api.Item;

/**
 * Sword to kill monsters.
 * Created by superernie77 on 12.12.2016.
 */
public class Sword implements Item {

    private String worldSymbol = "W";

    private String itemSymbol = "/";

    @Override
    public String getContent() {
        return worldSymbol;
    }

    @Override
    public void setContent(String character) {
        worldSymbol = character;
    }

    @Override
    public String getItemSymbol(){
        return itemSymbol;
    }
}
