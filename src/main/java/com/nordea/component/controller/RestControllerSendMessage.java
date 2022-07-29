package com.nordea.component.controller;

import com.nordea.component.model.Message;
import com.nordea.component.service.ProduceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestControllerSendMessage {

    @Autowired
    ProduceMessage produceMessage;

    @GetMapping(value = "/send/{salt}")
    public void sendSalt(@PathVariable String salt) {
        produceMessage.publishSaltToTopic(salt);
    }

    @PostMapping(value = "/send")
    public void sendMessage(@RequestBody Message message) {
        produceMessage.publishMessageTopic((message));

    }
}
