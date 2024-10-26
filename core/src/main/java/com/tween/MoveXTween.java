package com.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MoveXTween extends Tween {
    private final Actor actor;
    private final float start;
    private final float target;
    private final int align;
    public MoveXTween(Actor actor,float start,float target, float duration,int align) {
        super(duration);
        this.actor = actor;
        this.start = start;
        this.target = target;
        this.align = align;
    }
    @Override
    protected void onUpdate(float value) {
        float x = start + (target-start) * value;
        actor.setX(x,align);
    }
}
