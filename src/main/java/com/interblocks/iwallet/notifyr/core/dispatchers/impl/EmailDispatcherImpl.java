package com.interblocks.iwallet.notifyr.core.dispatchers.impl;

import com.interblocks.iwallet.notifyr.config.props.EmailDispatcherProperties;
import com.interblocks.iwallet.notifyr.core.dispatchers.EmailDispatcher;
import com.interblocks.iwallet.notifyr.core.dispatchers.impl.wrokers.EmailDispatcherWorker;
import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Data
@Slf4j
@Component
public class EmailDispatcherImpl implements EmailDispatcher<DefaultMessage> {

    private final EmailDispatcherProperties emailDispatcherProperties;
    private final EmailDispatcherWorker emailDispatcherWorker;

    private BlockingQueue<DefaultMessage> blockingQueue;

    @Autowired
    public EmailDispatcherImpl(EmailDispatcherProperties emailDispatcherProperties, EmailDispatcherWorker emailDispatcherWorker) {
        this.emailDispatcherProperties = emailDispatcherProperties;
        this.emailDispatcherWorker = emailDispatcherWorker;
    }

    public void start() {
        log.info("Email Dispatcher status: {}", emailDispatcherProperties.isEnabled() ? "ENABLED" : "DISABLED");
        log.info("Email Dispatcher Max queue size: {}", emailDispatcherProperties.getMaxQueueCapacity());
        if (emailDispatcherProperties.isEnabled()) {
            this.blockingQueue = new LinkedBlockingQueue<>(emailDispatcherProperties.getMaxQueueCapacity());
            this.emailDispatcherWorker.start(blockingQueue);
        }
    }

    @Override
    public void push(DefaultMessage defaultMessage) {
        try {
            this.blockingQueue.put(defaultMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public DefaultMessage pop() {
        return this.blockingQueue.remove();
    }

    @Override
    public void clear() {

    }
}

