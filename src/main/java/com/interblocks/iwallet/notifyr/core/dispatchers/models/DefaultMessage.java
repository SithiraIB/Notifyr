package com.interblocks.iwallet.notifyr.core.dispatchers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "notifyr-message")
public class DefaultMessage {
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "middle_name")
    private String middleName;
    @JsonProperty(value = "last_name")
    private String lastName;
    @JsonProperty(value = "need_response")
    private boolean needResponse;
}
