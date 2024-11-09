package com.werewolf.game.role;

public enum Team {
    VILLAGER,
    WOLF,
    SOLO;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
