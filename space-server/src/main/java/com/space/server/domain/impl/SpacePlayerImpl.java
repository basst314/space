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

    private Health playerHealth;

    private Integer playerId;

    private String content = "H";

    private List<Item> inventory = new ArrayList<>();

    private Item activeItem;

    private ItemUsage activeItemUsage = ItemUsage.STANDBY;

    private Direction direction = Direction.FORWARD;

    private boolean moved = false;

    private Step activeStep;

    public SpacePlayerImpl(){
        playerHealth = new Health();
        playerHealth.setHealth(3);
    }

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
    public void resetActivities() {
        this.setMoved(false);
        this.setActiveItemUsage(ItemUsage.STANDBY);
    }

    @Override
    public void setActiveItemUsed(boolean used) {
        activeItemUsage = used ? ItemUsage.IN_USE : ItemUsage.STANDBY;
    }

    @Override
    public ItemUsage getActiveItemUsage() {
        return activeItemUsage;
    }

    @Override
    public void setActiveItemUsage(ItemUsage usage) {
        activeItemUsage = usage;
    }

    /**
     * This player is ready to move, when it has not already been moved and is not currently using its active item
     *
     * @return
     */
    @Override
    public boolean isReadyToMove() {
        return !isMoved() && !ItemUsage.IN_USE.equals(activeItemUsage);
    }

    @Override
    public Health getHealth() { return playerHealth; }

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
    public int addItem(Item item) {
        inventory.add(item);
        return inventory.indexOf(item);
    }

    @Override
    public String getContent() {
        String health = getHealth().getContent();
        if (activeItem != null) {
            String item = activeItem.getItemSymbol(direction, activeItemUsage);
            return Direction.BACKWARD.equals(direction) ? item + content + health : health + content + item;
        }
        return Direction.BACKWARD.equals(direction) ? content + health : health + content;
    }

}