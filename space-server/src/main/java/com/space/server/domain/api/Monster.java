package com.space.server.domain.api;

import com.space.server.domain.impl.Health;

/**
 * Interface for a monster / opponent
 * Created by superernie77 on 16.12.2016.
 */
public interface Monster extends Overlay, Blockable {

    Health getHealth();

}
