package com.schambeck.dna.web.controller;

import com.schambeck.dna.web.client.Notification;
import com.schambeck.dna.web.client.NotificationClient;
import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.DnaDto;
import com.schambeck.dna.web.dto.PayloadDnaDto;
import com.schambeck.dna.web.dto.StatsDto;
import com.schambeck.dna.web.exception.MutantAlreadyExistsException;
import com.schambeck.dna.web.service.DnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.schambeck.dna.web.client.TypeNotification.SSE;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping("/mutant")
class DnaController {

    private final DnaService service;
    private final NotificationClient notificationClient;

    DnaController(DnaService service, NotificationClient notificationClient) {
        this.service = service;
        this.notificationClient = notificationClient;
    }

    @PostMapping
    ResponseEntity<DnaDto> create(@RequestBody @Valid PayloadDnaDto payload, Authentication authentication) {
        String[] dna = payload.getDna();
        Dna created = service.create(dna);
        notificationClient.send(createNotification(created));
        DnaDto representation = createDto(created);
        if (created.getMutant()) {
            return ResponseEntity.ok(representation);
        } else {
            return ResponseEntity.status(FORBIDDEN).body(representation);
        }
    }

    @GetMapping
    ResponseEntity<Page<DnaDto>> list(Pageable pageable) {
        Page<DnaDto> page = service.findAll(pageable).map(this::createDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    ResponseEntity<DnaDto> get(@PathVariable("id") UUID id) {
        DnaDto dto = service.findById(id)
                .map(this::createDto)
                .orElseThrow(() -> new RuntimeException(format("DNA not found: %s", id)));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/stats")
    @ResponseStatus(OK)
    StatsDto stats() {
        return service.stats();
    }

    @ExceptionHandler(MutantAlreadyExistsException.class)
    @ResponseStatus(value = CONFLICT, reason = "Dna already exists")
    void handleMutantAlreadyExistsException() {
    }

    private Notification createNotification(Dna dna) {
        return new Notification(SSE,
                "DNA created",
                format("DNA has been created: %s", dna.getId()),
                format("/mutant/%s", dna.getId()));
    }

    private DnaDto createDto(Dna created) {
        return new DnaDto(created.getId(), created.getDna(), created.getHash(), created.getMutant());
    }

}
