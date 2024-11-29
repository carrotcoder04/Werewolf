package com.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class SizeHeightTween extends Tween {
    private final Actor actor;
    private final float start;
    private final float target;
    public SizeHeightTween(Actor actor,float start,float target, float duration) {
        super(duration);
        this.actor = actor;
        this.start = start;
        this.target = target;
    }
    @Override
    protected void onUpdate(float value) {
        float height = start + (target-start) * value;
        actor.setHeight(height);
    }
}
