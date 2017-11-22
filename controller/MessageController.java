package com.mickl.messageservice.controller;

import com.mickl.messageservice.model.Message;
import com.mickl.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @PostMapping("/messages")
    public Mono<Message> sendMessage(@Valid @RequestBody Message message) {
        return messageRepository.save(message);
    }

    @DeleteMapping
    public void cleanMessages(){
        messageRepository.deleteAll();
    }
}
