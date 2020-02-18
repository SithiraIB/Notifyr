package com.interblocks.iwallet.notifyr.core.dispatchers;

public interface DispatchClient<T> {
    void start();

    void push(T t) throws InterruptedException;

    T pop();

    void clear();
}
