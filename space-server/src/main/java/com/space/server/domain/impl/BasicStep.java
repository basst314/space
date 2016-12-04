package com.space.server.domain.impl;

import com.space.server.domain.api.Step;

/**
 * Created by superernie77 on 04.12.2016.
 */
public class BasicStep implements Step {

    String content = ".";

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String character){
        if (character.length() > 1) {
            throw new IllegalArgumentException("Conent can only be one character long");
        }
        content = character;
    }

    @Override
    public boolean isConnector() {
        return false;
    }
}
