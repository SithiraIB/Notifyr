package com.interblocks.iwallet.notifyr.core.dispatchers.impl.wrokers;

import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.services.SMSDeliverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class SMSDispatcherWorker {

    private SMSDeliverService<DefaultMessage> smsService;

    @Autowired
    public SMSDispatcherWorker(SMSDeliverService<DefaultMessage> smsService) {
        this.smsService = smsService;
    }

    public void start(BlockingQueue<DefaultMessage> blockingQueue) {
        Thread thread = new Thread(() -> {
            while (true) {
                Date now = new Date();
                try {
                    DefaultMessage defaultMessage = blockingQueue.take();
                    log.info("SMS Worker started JOB at : {}", now);
                    log.info("Data -> {}", defaultMessage);
                    smsService.submit(defaultMessage);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
