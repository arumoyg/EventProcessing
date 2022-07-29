package com.nordea.component;

import com.nordea.component.model.Message;
import com.nordea.component.model.Salt;
import com.nordea.component.service.ConsumeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComponentApplicationTests {



    @Autowired
    ConsumeMessage consumeMessage;

    @Test
    void contextLoads() {
    }

    @Test
    void testSink() {

        Message message = new Message();
        message.setId(1L);
        message.setMessage("password1111");

        Salt salt = new Salt();
        salt.setSalt("salty");

        consumeMessage.consumeSalt(salt);
        consumeMessage.consumeJsonMessage(message);

    }

}
