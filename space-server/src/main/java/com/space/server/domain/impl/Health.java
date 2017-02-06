package com.space.server.domain.impl;

/**
 * Stores and manages the health of an hero or monster
 * Created by superernie77 on 26.01.2017.
 */
public class Health {

    private int health;

    public String getContent(){
        if (health == 3){
            return "³";
        } else if (health == 2) {
            return "²";
        } else {
            return "¹";
        }
    }

    public boolean isDead(){
        return health <= 0;
    }

    public void processHit(){
        health--;
    }

    public void processHeal(){
        if (health < 3) {
            health++;
        }
    }

    public void setHealth(int i) {
        health = i;
    }
}
