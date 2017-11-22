package com.mickl.messageservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class Message {
    @Id
    private String id;
    @NotNull
    private String user_id;
    @NotNull
    private String username;
    private String message;

    @JsonCreator
    public Message(String id, @NotNull String user_id, @NotNull String username, String message) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.message = message;
    }
}
