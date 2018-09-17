package com.example.springonedemo;

import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.io.ResourceLoader;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@EnableBinding(Source.class)
@Component
public class NotificationSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationSender.class);
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    Source sender;
    @Autowired
    MessageChannel output;

    public void doSend(String blobLocation) {
        //this.sender.output().send(new GenericMessage<>(blobLocation));
        this.output.send(new GenericMessage<>(blobLocation));
        LOGGER.info("Payload: '" + blobLocation + "' sent!");

    }
}
