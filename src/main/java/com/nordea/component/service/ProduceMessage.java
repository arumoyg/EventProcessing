package com.nordea.component.service;

import com.nordea.component.model.Message;
import com.nordea.component.model.Sink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessage {

    @Autowired
    public KafkaTemplate<String, String> kafkaTemp;

    @Autowired
    public KafkaTemplate<String, Message> messageKafkaTemp;

    @Autowired
    public KafkaTemplate<String, Sink> sinkKafkaTemp;

    public void publishSaltToTopic(String salt) {
        System.out.println("Publishing Salt to topic "+salt);
        this.kafkaTemp.send("salt",salt);
    }

    public void publishMessageTopic(Message message) {
        System.out.println("Publishing to topic "+message);
        this.messageKafkaTemp.send("onmessage_json",message);
    }

    public void publishToSink(Sink sink) {
        System.out.println("Publishing to sink topic. Sink = "+sink);
        this.sinkKafkaTemp.send("sink_json",sink);
    }

}
