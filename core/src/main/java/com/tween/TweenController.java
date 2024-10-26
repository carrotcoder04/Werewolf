package com.tween;

import java.util.ArrayList;

public class TweenController {
    private static ArrayList<Tween> tweens;
    static  {
        tweens = new ArrayList<>();
    }
    public static void update(float deltaTime) {
        for(int i = tweens.size() - 1; i >= 0; i--) {
            Tween tween = tweens.get(i);
            if(tween.isTweening()) {
                tween.update(deltaTime);
            }
            else {
                tweens.remove(i);
            }
        }
    }
    static void addTween(Tween tween) {
        tweens.add(tween);
    }
}
