package com.space.server.domain.items.impl;

import com.space.server.domain.api.Direction;
import com.space.server.domain.api.Weapon;
import com.space.server.domain.items.api.ItemUsage;

import java.util.HashMap;
import java.util.Map;

/**
 * Sword to kill monsters.
 * Created by superernie77 on 12.12.2016.
 */
public class Sword implements Weapon {

    private String worldSymbol = "W";

    private Map<Direction, Map<ItemUsage, String>> itemSymbols = new HashMap<>();

    public Sword() {
        Map<ItemUsage, String> forwardSymbols = new HashMap<>();
        forwardSymbols.put(ItemUsage.STANDBY, "/");
        forwardSymbols.put(ItemUsage.IN_USE, "-");

        Map<ItemUsage, String> backwardSymbols = new HashMap<>();
        backwardSymbols.put(ItemUsage.STANDBY, "\\");
        backwardSymbols.put(ItemUsage.IN_USE, "-");

        itemSymbols.put(Direction.FORWARD, forwardSymbols);
        itemSymbols.put(Direction.BACKWARD, backwardSymbols);
    }

    @Override
    public String getContent() {
        return worldSymbol;
    }

    @Override
    public void setContent(String character) {
        worldSymbol = character;
    }

    @Override
    public String getItemSymbol(Direction direction, ItemUsage usage) {
        return itemSymbols.get(direction).get(usage);
    }
}
