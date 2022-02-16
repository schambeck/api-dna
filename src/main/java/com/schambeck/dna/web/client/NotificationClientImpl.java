package com.schambeck.dna.web.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationClientImpl implements NotificationClient {

    private static final String RESOURCE = "/notifications";
    private final RestTemplate restTemplate;

    public NotificationClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Notification send(Notification notification) {
        return restTemplate.postForObject(RESOURCE, notification, Notification.class);
    }

}
