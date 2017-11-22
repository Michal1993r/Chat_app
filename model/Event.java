package com.mickl.messageservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mickl.messageservice.EventBuilder;

import java.util.concurrent.atomic.AtomicInteger;

public class Event {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private final int id;
    private final long timestamp;
    private Type type;
    private Payload payload;

    @JsonCreator
    public Event(@JsonProperty("type") Type type,
                 @JsonProperty("payload") Payload payload) {
        this.type = type;
        this.payload = payload;
        this.id = ID_GENERATOR.addAndGet(1);
        this.timestamp = System.currentTimeMillis();
    }

    public static EventBuilder type(Type type) {
        return new EventBuilder().type(type);
    }

    public Type getType() {
        return type;
    }

    public Payload getPayload() {
        return payload;
    }

    @JsonIgnore
    public User getUser() {
        return getPayload().getUser();
    }

    public int getId() {
        return id;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public enum Type {
        CHAT_MESSAGE, USER_JOINED, USER_STATS, USER_LEFT;
    }
}
