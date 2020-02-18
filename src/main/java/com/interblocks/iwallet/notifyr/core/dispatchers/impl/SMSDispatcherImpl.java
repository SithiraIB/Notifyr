package com.interblocks.iwallet.notifyr.core.dispatchers.impl;

import com.interblocks.iwallet.notifyr.config.props.SMSDispatcherProperties;
import com.interblocks.iwallet.notifyr.core.dispatchers.SMSDispatcher;
import com.interblocks.iwallet.notifyr.core.dispatchers.impl.wrokers.SMSDispatcherWorker;
import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class SMSDispatcherImpl implements SMSDispatcher<DefaultMessage> {
    private final SMSDispatcherProperties smsDispatcherProperties;
    private final SMSDispatcherWorker smsDispatcherWorker;
    private BlockingQueue<DefaultMessage> blockingQueue;

    @Autowired
    public SMSDispatcherImpl(SMSDispatcherProperties smsDispatcherProperties, SMSDispatcherWorker smsDispatcherWorker) {
        this.smsDispatcherProperties = smsDispatcherProperties;
        this.smsDispatcherWorker = smsDispatcherWorker;
    }

    @Override
    public void start() {
        log.info("SMS Dispatcher status: {}", smsDispatcherProperties.isEnabled() ? "ENABLED" : "DISABLED");
        log.info("SMS Dispatcher Max queue size: {}", smsDispatcherProperties.getMaxQueueCapacity());
        if (smsDispatcherProperties.isEnabled()) {
            blockingQueue = new LinkedBlockingQueue<>();
            smsDispatcherWorker.start(blockingQueue);
        }
    }

    @Override
    public void push(DefaultMessage defaultMessage) throws InterruptedException {
        this.blockingQueue.put(defaultMessage);
    }

    @Override
    public DefaultMessage pop() {
        return this.blockingQueue.remove();
    }

    @Override
    public void clear() {
        this.blockingQueue.forEach(defaultMessage -> this.blockingQueue.remove(defaultMessage));
    }
}

