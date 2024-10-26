package com.tween;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.until.Vector2Untils;

public class ScaleTween extends Tween {
    private final Actor actor;
    private final Vector2 start;
    private final Vector2 target;

    public ScaleTween(Actor actor, Vector2 start, Vector2 target, float duration) {
        super(duration);
        this.actor = actor;
        this.start = start.cpy();
        this.target = target.cpy();
    }

    @Override
    protected void onUpdate(float value) {
        Vector2 scale = Vector2Untils.lerp(start,target,value);
        actor.setScale(scale.x, scale.y);
    }
}
