package com.space.server.domain.impl;

import com.space.server.domain.api.Overlay;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of the step interface
 * Created by superernie77 on 04.12.2016.
 */
public class BasicStep implements Step {

    private String content = ".";

    private Step next;

    private Step previous;

    private List<Overlay> overlays = new ArrayList<>();

    @Override
    public String getContent() {
        if (overlays.size() > 0){
            return overlays.stream().map(s -> s.getContent()).collect(Collectors.joining());
        }
        return content;
    }


    public void setContent(String character){

        content = character;
    }

    @Override
    public boolean isConnector() {
        return false;
    }

     @Override
    public Step next(){
        return next;
    }

    @Override
    public Step previous(){
        return previous;
    }

    @Override
    public void setNext(Step v_next){
        next = v_next;
    }

    @Override
    public void setPrevious(Step v_previous){
        previous = v_previous;
    }

    @Override
    public void addOverlay(Overlay over) {
        overlays.add(over);
    }

    @Override
    public boolean isPlayerPresent(){
        return overlays.stream().anyMatch( o -> o instanceof SpacePlayer );
    }

    @Override
    public List<Overlay> getOverlays(){ return overlays;}
}
