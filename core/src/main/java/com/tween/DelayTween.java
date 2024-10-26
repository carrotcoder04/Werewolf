package com.tween;

import com.event.interfaces.Action;

public class DelayTween extends Tween {
    public DelayTween(float duration, Action completed) {
        super(duration);
        onComplete(completed);
    }

    @Override
    protected void onUpdate(float value) {

    }
}
