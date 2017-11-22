package com.mickl.messageservice;

import com.mickl.messageservice.model.Event;
import com.mickl.messageservice.model.Payload;

public class EventBuilder {
    private Event.Type type;
    private PayloadBuilder payloadBuilder = new PayloadBuilder(this);

    public EventBuilder type(Event.Type type) {
        this.type = type;
        return this;
    }

    public PayloadBuilder withPayload() {
        return payloadBuilder;
    }

    Event buildEvent(Payload payload) {
        return new Event(type, payload);
    }
}
