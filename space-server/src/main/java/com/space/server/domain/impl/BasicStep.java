package com.space.server.domain.impl;

import com.space.server.domain.api.Step;

/**
 * Basic implementation of the step interface
 * Created by superernie77 on 04.12.2016.
 */
public class BasicStep implements Step {

    private String content = ".";

    private Step next;

    private Step previous;

    @Override
    public String getContent() {
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
}
