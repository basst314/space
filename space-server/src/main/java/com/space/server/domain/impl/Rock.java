package com.space.server.domain.impl;

import com.space.server.domain.api.Blockable;
import com.space.server.domain.api.Overlay;

/**
 * Created by superernie77 on 17.02.2017.
 */
public class Rock implements Blockable, Overlay {
    @Override
    public String getContent() {
        return "R";
    }
}
