package com.interblocks.iwallet.notifyr.core.dispatchers.facades;

import com.interblocks.iwallet.notifyr.core.dispatchers.EmailDispatcher;
import com.interblocks.iwallet.notifyr.core.dispatchers.SMSDispatcher;
import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DispatcherFacade {

    private final SMSDispatcher<DefaultMessage> smsDispatcher;
    private final EmailDispatcher<DefaultMessage> emailDispatcher;

    @Autowired
    public DispatcherFacade(SMSDispatcher<DefaultMessage> smsDispatcher, EmailDispatcher<DefaultMessage> emailDispatcher) {
        this.smsDispatcher = smsDispatcher;
        this.emailDispatcher = emailDispatcher;
    }

    /**
     * Starts all registered and enabled dispatchers.
     */
    public void startAll() {
        log.info("Starting all dispatchers...");
        this.emailDispatcher.start();
        this.smsDispatcher.start();
        log.info("Dispatchers started...");
    }
}
