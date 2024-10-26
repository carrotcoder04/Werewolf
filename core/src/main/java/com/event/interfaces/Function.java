package com.event.interfaces;
@FunctionalInterface
public interface Function<T,V> {
    T invoke(V param);
}
