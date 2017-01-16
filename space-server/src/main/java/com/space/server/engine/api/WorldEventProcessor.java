package com.space.server.engine.api;

import com.space.server.domain.api.SpacePlayer;
import com.space.server.engine.api.WorldEvent;

import java.util.List;

/**
 * Created by superernie77 on 29.12.2016.
 */
public interface WorldEventProcessor {

    void processEvents(List<WorldEvent> events, SpacePlayer player);
}
