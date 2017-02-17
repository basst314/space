package com.space.server.domain.api;

/**
 * Created by superernie77 on 16.02.2017.
 */
public interface Door extends Overlay {

    Step getTargetStep();

    Step getSourceStep();
}
