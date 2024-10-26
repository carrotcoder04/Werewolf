package com.tween;

import com.badlogic.gdx.math.MathUtils;
import com.event.interfaces.Action;
import com.event.interfaces.Function;

public abstract class Tween {
    private Function<Float,Float> easeFunction;
    private Function<Float,Float> forwardFunction;
    private Function<Float,Float> reverseFunction;
    private boolean isTweening;
    private float currentTime;
    private final float duration;
    private boolean isLoop;
    private float limit;
    private Action completeAction;
    private Action killAction;
    private LoopType loopType;
    public Tween(float duration) {
        this.duration = duration;
        loopType = LoopType.Yoyo;
        limit = 1;
        setEase(Ease.LINEAR);
    }
    final void update(float deltaTime) {
        float value = MathUtils.clamp(easeFunction.invoke(currentTime/duration),0,1);
        onUpdate(value);
        if(value == limit) {
            if(isLoop) {
                reset();
            }
            else {
                isTweening = false;
            }
            if(completeAction != null) {
                completeAction.invoke();
            }
        }
        currentTime += deltaTime;
    }
    public void setLoopType(LoopType loopType) {
        this.loopType = loopType;
    }
    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }
    public void start() {
        isTweening = true;
        TweenController.addTween(this);
    }
    boolean isTweening() {
        return isTweening;
    }
    private  void reset() {
        currentTime = 0;
        if(loopType == LoopType.Yoyo) {
            easeFunction = limit == 1 ? reverseFunction : forwardFunction;
            limit = limit == 1 ? 0 : 1;
        }
    }

    public void setEase(Ease ease) {
        switch (ease) {
            case LINEAR:
                easeFunction = Tween::easeLinear;
                break;
            case IN_SINE:
                easeFunction = Tween::easeInSine;
                break;
            case OUT_SINE:
                easeFunction = Tween::easeOutSine;
                break;
            case IN_OUT_SINE:
                easeFunction = Tween::easeInOutSine;
                break;
            case IN_QUAD:
                easeFunction = Tween::easeInQuad;
                break;
            case OUT_QUAD:
                easeFunction = Tween::easeOutQuad;
                break;
            case IN_OUT_QUAD:
                easeFunction = Tween::easeInOutQuad;
                break;
            case IN_CUBIC:
                easeFunction = Tween::easeInCubic;
                break;
            case OUT_CUBIC:
                easeFunction = Tween::easeOutCubic;
                break;
            case IN_OUT_CUBIC:
                easeFunction = Tween::easeInOutCubic;
                break;
            default:
                throw new IllegalArgumentException("Unsupported ease: " + ease);
        }
        forwardFunction = easeFunction;
        reverseFunction = value -> (1 - forwardFunction.invoke(value));
    }

    public void kill() {
        isTweening = false;
        if(killAction != null) {
            killAction.invoke();
        }
    }
    public void onComplete(Action action) {
        completeAction = action;
    }
    public void onKill(Action action) {
        killAction = action;
    }
    private static float easeLinear(float t) {
        return t;
    }
    private static float easeInSine(float t) {
        return (float) (1f - Math.cos((t*Math.PI)/2f));
    }
    private static float easeOutSine(float t) {
        return (float) (Math.sin((t * Math.PI) / 2f));
    }
    private static float easeInOutSine(float t) {
        return (float) (-(Math.cos(Math.PI * t) - 1f) / 2f);
    }
    private static float easeInQuad(float t) {
        return t*t;
    }
    private static float easeOutQuad(float t) {
        return 1f - (1f - t) * (1f - t);
    }
    private static float easeInOutQuad(float t) {
        return t < 0.5f ? 2f * t * t : (float) (1f - Math.pow(-2f * t + 2f, 2f) / 2f);
    }
    private static float easeInCubic(float t) {
        return t * t * t;
    }
    private static float easeOutCubic(float t) {
        return (float) (1f - Math.pow(1 - t, 3));
    }
    private static float easeInOutCubic(float t) {
        return t < 0.5 ? 4 * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 3) / 2);
    }
    protected abstract void onUpdate(float value);
}
