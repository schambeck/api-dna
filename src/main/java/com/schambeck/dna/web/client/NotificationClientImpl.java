package com.schambeck.dna.web.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class NotificationClientImpl implements NotificationClient {

    private static final String RESOURCE = "/notifications";
    private final RestTemplate restTemplate;

    public NotificationClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Notification> send(Notification notification, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("Authorization", "Bearer ".concat(token));
        HttpEntity<Notification> entity = new HttpEntity<>(notification, headers);
        return restTemplate.exchange(RESOURCE, POST, entity, Notification.class);
    }

}
