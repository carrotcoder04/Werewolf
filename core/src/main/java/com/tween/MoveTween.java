package com.tween;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.until.Vector2Untils;

public class MoveTween extends Tween {
    private final Actor actor;
    private final Vector2 start;
    private final Vector2 target;
    private final int align;

    public MoveTween( Actor actor, Vector2 start, Vector2 target,float duration, int align) {
        super(duration);
        this.actor = actor;
        this.start = start.cpy();
        this.target = target.cpy();
        this.align = align;
    }
    @Override
    protected void onUpdate(float value) {
        Vector2 position = Vector2Untils.lerp(start, target, value);
        actor.setPosition(position.x, position.y,align);
    }
}
