package com.space.server.domain.impl;

import com.space.server.domain.api.Direction;
import com.space.server.domain.api.Item;
import com.space.server.domain.api.SpacePlayer;
import com.space.server.domain.api.Step;
import com.space.server.domain.items.api.ItemUsage;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a space player.
 * Created by superernie77 on 11.12.2016.
 */
public class SpacePlayerImpl implements SpacePlayer{

    private Integer playerId;

    private String content = "H";

    private List<Item> inventory = new ArrayList<>();

    private Item activeItem;

    private Direction direction = Direction.FORWARD;

    private boolean moved = false;

    private Step activeStep;

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isMoved() {
        return moved;
    }

    @Override
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public Step getActiveStep() {
        return activeStep;
    }

    @Override
    public void setActiveStep(Step step) {
        activeStep = step;
    }

    @Override
    public Integer getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(Integer id) {
        playerId = id;
    }

    @Override
    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public Item getItem(int no) {
        return inventory.get(no);
    }

    @Override
    public Item getActiveItem() {
        return activeItem;
    }

    @Override
    public void setActiveItem(int no) {
        activeItem = inventory.get(no);
    }

    @Override
    public void addItem(Item item) {
        inventory.add(item);
    }

    @Override
    public String getContent() {
        if (activeItem != null){
			String item = activeItem.getItemSymbol(direction, ItemUsage.STANDBY);
			return Direction.BACKWARD.equals(direction) ? item + content : content + item;
        }
        return content;
    }

    @Override
    public void setContent(String character) {
        content = character;
    }
}