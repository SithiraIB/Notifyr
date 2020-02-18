package com.interblocks.iwallet.notifyr.controllers;

import com.interblocks.iwallet.notifyr.core.controllers.BaseNotificationController;
import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.services.impl.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/notifications/sms")
public class SMSNotificationController implements BaseNotificationController<DefaultMessage> {

    private final SMSService smsService;

    @Autowired
    public SMSNotificationController(SMSService smsService) {
        this.smsService = smsService;
    }

    @Override
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public ResponseEntity<Object> send(@Valid @RequestBody DefaultMessage message) {
        return ResponseEntity.status(HttpStatus.OK).body(this.smsService.send(message));
    }

}
