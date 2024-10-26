package com.event.listener;

import com.event.interfaces.Function;

import java.util.ArrayList;

public class EventListener<T,V> {
    private final ArrayList<Function<T,V>> eventList;
    public EventListener() {
        eventList = new ArrayList<>();
    }
    public void addEvent(Function<T,V> event) {
        eventList.add(event);
    }
    public void removeEvent(Function<T,V> event) {
        eventList.remove(event);
    }
    public void clearEventList() {
        eventList.clear();
    }
    public void invoke(V arg) {
        for (Function<T,V> event : eventList) {
            event.invoke(arg);
        }
    }
}
