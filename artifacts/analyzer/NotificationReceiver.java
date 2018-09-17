package com.example.springonedemo;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


import java.io.IOException;
import java.net.URISyntaxException;

@EnableBinding(Sink.class)
public class NotificationReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationReceiver.class);

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ImageAnalysisService svc;

    @StreamListener(Sink.INPUT)
    public void onReceive(String blobLocaiton) throws IOException, URISyntaxException, JSONException {
        LOGGER.info("Received: " + blobLocaiton);
        Resource blobResource = this.resourceLoader.getResource(blobLocaiton);
        String res = this.svc.analyze(blobResource.getURL().toString());
        LOGGER.info(res);
    }
}
