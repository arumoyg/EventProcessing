package com.nordea.component.service;

import com.nordea.component.model.Message;
import com.nordea.component.model.Salt;
import com.nordea.component.model.Sink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@EnableKafka
public class ConsumeMessage {

    private String latestMessage = "";

    @Autowired
    ProduceMessage produceMessage;


    //Consume Salt
    @KafkaListener(topics = "salt", groupId = "group_id", containerFactory = "saltKafkaListenerContainerFactory")
    public void consumeSalt(Salt salt) {
        System.out.println("Salt Received::" +salt);
        latestMessage = salt.getSalt();
    }

    //Consume Message
    @KafkaListener(topics = "onmessage_json", groupId = "group_json", containerFactory = "messageKafkaListenerContainerFactory")
    public void consumeJsonMessage(Message message) {
        System.out.println("Message Received" +message.getMessage());
        applySha256(message, latestMessage, 5000);
    }

    //Apply 5000 rounds of SHA256
    private void applySha256(Message message, String salt, int rounds) {
        try {
            //if there is no salt value, then we generate a salt value randomly.
            if (salt.equals("")) {
                salt = UUID.randomUUID().toString();
            }
            String msg = message.getMessage();

            for (int i = 0; i < rounds; i++) {
                String messageString = msg + salt;
                byte[] combined = messageString.getBytes(StandardCharsets.UTF_8);
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(combined);
                byte[] hash = md.digest();
                msg = Base64.getEncoder().encodeToString(hash);
            }
            produceMessage.publishToSink(new Sink(message.getId(), message.getMessage().getBytes(), salt.getBytes(), msg.getBytes()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
