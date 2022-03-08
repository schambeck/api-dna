package com.schambeck.dna.web.client;

import org.springframework.http.ResponseEntity;

public interface NotificationClient {

    ResponseEntity<Notification> send(Notification notification, String token);

}
