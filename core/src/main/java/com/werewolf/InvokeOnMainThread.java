package com.werewolf;

import com.event.interfaces.Action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InvokeOnMainThread {
    private static Queue<Action> waitFunctions;
    static {
        waitFunctions = new ConcurrentLinkedQueue<>();
    }
    static void update() {
        while (!waitFunctions.isEmpty()) {
            waitFunctions.poll().invoke();
        }
    }
    public static void invoke(Action action) {
        waitFunctions.offer(action);
    }
}
