package com.mickl.messageservice;

import com.mickl.messageservice.model.Event;
import com.mickl.messageservice.model.Payload;
import com.mickl.messageservice.model.User;

import java.util.HashMap;
import java.util.Map;

public class PayloadBuilder {

    private String alias;
    private String avatar;
    private Map<String, Object> properties = new HashMap<>();
    private EventBuilder builder;

    PayloadBuilder(EventBuilder buildEvent) {
        this.builder = buildEvent;
    }

    public PayloadBuilder userAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public PayloadBuilder userAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public PayloadBuilder user(User user) {
        this.alias = user.getAlias();
        this.avatar = user.getAvatar();
        return this;
    }

    public PayloadBuilder systemUser() {
        user(User.systemUser());
        return this;
    }

    public PayloadBuilder property(String property, Object value) {
        properties.put(property, value);
        return this;
    }

    public Event build() {
        return builder.buildEvent(new Payload(new User(this.alias, this.avatar), properties));
    }
}