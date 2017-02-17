package com.space.server.domain.impl;

import com.space.server.domain.api.Direction;
import com.space.server.domain.api.Item;
import com.space.server.domain.items.api.ItemUsage;

/**
 * Created by superernie77 on 17.02.2017.
 */
public class Princess implements Item {

    private String content = "P";

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String character) {
        content = character;
    }

    @Override
    public String getItemSymbol(Direction direction, ItemUsage usage) {
        return "P";
    }
}
