package com.mickl.messageservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mickl.messageservice.model.Message;
import com.mickl.messageservice.repository.MessageRepository;
import com.mickl.messageservice.subscriber.MessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class MessageWebSocketHandler implements WebSocketHandler {

    private final MessageRepository messageRepository;
    private MessageSubscriber subscriber;
    private ObjectMapper mapper;

    @Autowired
    public MessageWebSocketHandler(MessageRepository messageRepository) {
        this.subscriber = new MessageSubscriber(messageRepository);
        this.messageRepository = messageRepository;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(this::toMessage)
                .subscribe(subscriber::onNext, subscriber::onError, subscriber::onComplete);
        Flux<Message> messageFlux = messageRepository.findAllByUsernameIsNotNull();
        return session.send(
                messageFlux
                        .map(this::toJSON)
                        .map(session::textMessage));
    }

    private String toJSON(Message message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message toMessage(String json) {
        try {
            return mapper.readValue(json, Message.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON:" + json, e);
        }
    }

}
