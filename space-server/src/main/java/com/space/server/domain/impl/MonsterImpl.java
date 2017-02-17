package com.space.server.domain.impl;

import com.space.server.domain.api.Monster;

/**
 * Basic space monster
 * Created by superernie77 on 16.12.2016.
 */
public class MonsterImpl implements Monster {

    private Health monsterHealth;

    public MonsterImpl(){
        monsterHealth = new Health();
        monsterHealth.setHealth(1);
    }

    public Health getHealth(){
        return monsterHealth;
    }

    private String content = "M";

    @Override
    public String getContent() {
        return content + monsterHealth.getContent();
    }

    @Override
    public void setContent(String character) {
        content = character;
    }
}
