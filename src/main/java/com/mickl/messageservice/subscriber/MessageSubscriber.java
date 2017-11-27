package com.mickl.messageservice.subscriber;

import com.mickl.messageservice.model.Message;
import com.mickl.messageservice.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
public class MessageSubscriber {

    private final MessageRepository messageRepository;

    public MessageSubscriber(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void onNext(@Valid Message message) {
        messageRepository.save(message)
                .subscribe();
    }

    public void onError(Throwable error) {
        log.error(error.getMessage());
    }

    public void onComplete() {
        log.info("Web Socket Complete!");
    }

}