package com.schambeck.dna.web.controller;

import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.DnaDto;
import com.schambeck.dna.web.dto.PayloadDnaDto;
import com.schambeck.dna.web.dto.StatsDto;
import com.schambeck.dna.web.exception.MutantAlreadyExistsException;
import com.schambeck.dna.web.service.DnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping("/mutant")
class DnaController {

    private final DnaService service;

    DnaController(DnaService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<DnaDto> create(@RequestBody @Valid PayloadDnaDto payload) {
        String[] dna = payload.getDna();
        Dna created = service.create(dna);
        DnaDto representation = new DnaDto(created.getId(), created.getDna(), created.getHash(), created.getMutant());
        if (created.getMutant()) {
            return ResponseEntity.ok(representation);
        } else {
            return ResponseEntity.status(FORBIDDEN).body(representation);
        }
    }

    @GetMapping
    ResponseEntity<Page<DnaDto>> list(Pageable pageable) {
        Page<DnaDto> page = service.findAll(pageable).map(dna -> new DnaDto(dna.getId(), dna.getDna(), dna.getHash(), dna.getMutant()));
        return ResponseEntity.ok(page);
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

}
