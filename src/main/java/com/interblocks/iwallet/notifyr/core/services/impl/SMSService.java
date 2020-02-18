package com.interblocks.iwallet.notifyr.core.services.impl;

import com.interblocks.iwallet.notifyr.core.dispatchers.SMSDispatcher;
import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.services.SMSDeliverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SMSService {

    private final SMSDeliverService<DefaultMessage> deliverService;
    private final SMSDispatcher<DefaultMessage> dispatcher;

    @Autowired
    public SMSService(SMSDeliverService<DefaultMessage> deliverService, SMSDispatcher<DefaultMessage> dispatcher) {
        this.deliverService = deliverService;
        this.dispatcher = dispatcher;
    }

    public Object send(DefaultMessage message) {
        if (!message.isNeedResponse()) {
            try {
                this.dispatcher.push(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        log.info("Parsing job to Delivery service...");
        return this.deliverService.submit(message);
    }
}
