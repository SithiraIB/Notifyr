package com.interblocks.iwallet.notifyr.core.dispatchers.impl.wrokers;

import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.services.SMSDeliverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class EmailDispatcherWorker {

    private final SMSDeliverService<DefaultMessage> deliverService;

    @Autowired
    public EmailDispatcherWorker(SMSDeliverService<DefaultMessage> deliverService) {
        this.deliverService = deliverService;
    }

    public void start(BlockingQueue<DefaultMessage> blockingQueue) {
        log.info("Started Email Dispatcher");
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    DefaultMessage defaultMessage = blockingQueue.take();
                    //deliverService.send(defaultMessage);
                    log.info("Executing JOB ..... ");
                    log.info("Remaining number of jobs: {}", blockingQueue.size());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
