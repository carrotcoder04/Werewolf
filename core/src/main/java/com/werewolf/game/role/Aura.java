package com.werewolf.game.role;

public enum Aura {
    GOOD("Thiện"),
    EVIL("Ác"),
    UNKNOWN("Không Rõ");
    private final String detail;
    Aura(String detail) {
        this.detail = detail;
    }
    public String getDetail() {
        return detail;
    }
    @Override
    public String toString() {
        return detail;
    }
}
