package com.space.server.domain.impl;

import com.space.server.domain.api.Monster;

/**
 * Basic space monster
 * Created by superernie77 on 16.12.2016.
 */
public class BasicMonster implements Monster {

    private String content = "M";

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String character) {
        content = character;
    }
}
