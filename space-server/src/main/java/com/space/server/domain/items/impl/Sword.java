package com.space.server.domain.items.impl;

import com.space.server.domain.api.Item;

/**
 * Created by superernie77 on 12.12.2016.
 */
public class Sword implements Item {

    private String sword = "/";

    @Override
    public String getContent() {
        return sword;
    }

    @Override
    public void setContent(String character) {
        sword = character;
    }
}
