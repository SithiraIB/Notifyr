package com.interblocks.iwallet.notifyr.services.delivery;

import com.interblocks.iwallet.notifyr.core.dispatchers.models.DefaultMessage;
import com.interblocks.iwallet.notifyr.core.services.EmailDeliverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailDeliveryServiceImpl implements EmailDeliverService<DefaultMessage> {

    @Override
    public Object submit(DefaultMessage payload) {
        return null;
    }
}
