package com.tween;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FadeTween extends Tween {
    private final Actor actor;
    private final float start;
    private final float target;
    public FadeTween(Actor actor,float start,float target, float duration) {
        super(duration);
        this.actor = actor;
        this.start = start;
        this.target = target;
    }
    @Override
    protected void onUpdate(float value) {
        Color color = actor.getColor();
        color.a = start + (target-start) * value;
        actor.setColor(color);
    }
}
