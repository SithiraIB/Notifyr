package com.interblocks.iwallet.notifyr.services.delivery;

import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.services.SMSDeliverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("standalone")
public class SMSDeliverServiceImpl implements SMSDeliverService<DefaultMessage> {

    @Override
    public Object submit(DefaultMessage payload) {
        log.info("Attempting to send SMS");
        return null;
    }
}
