package com.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MoveYTween extends Tween {
    private final Actor actor;
    private final float start;
    private final float target;
    private final int align;
    public MoveYTween(Actor actor,float start,float target, float duration,int align) {
        super(duration);
        this.actor = actor;
        this.start = start;
        this.target = target;
        this.align = align;
    }
    @Override
    protected void onUpdate(float value) {
        float y = start + (target-start) * value;
        actor.setY(y,align);
    }
}
