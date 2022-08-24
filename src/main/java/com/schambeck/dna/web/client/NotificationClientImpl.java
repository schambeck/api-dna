package com.schambeck.dna.web.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationClientImpl implements NotificationClient {

    private static final String RESOURCE = "/notifications";

    private final RestTemplate restTemplate;

    @Value("${custom.api-notification.service-id}")
    private String serviceId;

    public NotificationClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Notification send(Notification notification) {
        return restTemplate.postForObject(serviceId.concat(RESOURCE), notification, Notification.class);
    }

}
