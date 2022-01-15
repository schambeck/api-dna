package com.schambeck.dna.web.controller;

import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.DnaDto;
import com.schambeck.dna.web.dto.PayloadDnaDto;
import com.schambeck.dna.web.dto.StatsDto;
import com.schambeck.dna.web.exception.MutantAlreadyExistsException;
import com.schambeck.dna.web.service.DnaService;
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
    @ResponseStatus(CREATED)
    DnaDto create(@RequestBody @Valid PayloadDnaDto payload) {
        String[] dna = payload.getDna();
        Dna created = service.create(dna);
        return new DnaDto(created.getId(), created.getDna(), created.getMutant());
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
