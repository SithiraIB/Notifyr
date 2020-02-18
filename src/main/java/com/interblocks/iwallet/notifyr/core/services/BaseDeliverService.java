package com.interblocks.iwallet.notifyr.core.services;

public interface BaseDeliverService<T> {
    Object submit(T payload);
}
