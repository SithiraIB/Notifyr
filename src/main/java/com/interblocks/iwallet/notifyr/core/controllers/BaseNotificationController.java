package com.interblocks.iwallet.notifyr.core.controllers;

import org.springframework.http.ResponseEntity;

public interface BaseNotificationController<T> {
    ResponseEntity<Object> send(T payload);
}
