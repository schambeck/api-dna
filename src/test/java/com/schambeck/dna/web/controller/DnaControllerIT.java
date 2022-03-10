package com.schambeck.dna.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schambeck.dna.web.client.NotificationClient;
import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.DnaDto;
import com.schambeck.dna.web.dto.PayloadDnaDto;
import com.schambeck.dna.web.dto.StatsDto;
import com.schambeck.dna.web.exception.DnaRequiredException;
import com.schambeck.dna.web.exception.MutantAlreadyExistsException;
import com.schambeck.dna.web.exception.NotSquareException;
import com.schambeck.dna.web.service.DnaServiceImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import static com.schambeck.dna.web.util.test.DnaUtil.*;
import static com.schambeck.dna.web.util.test.StatsUtil.assertStatsDto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DnaController.class)
class DnaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DnaServiceImpl service;

    @MockBean
    private NotificationClient notificationClient;

    @Test
    void createMutant() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        Dna mockDna = createDna("18988518-c010-451b-8489-b14d92a0afd8", payload.getDna(), "10", true);
        DnaDto dto = createDnaDto(mockDna.getId(), mockDna.getDna(), mockDna.getHash(), mockDna.getMutant());
        when(service.create(payload.getDna())).thenReturn(mockDna);

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isOk())
                .andExpectAll(assertDnaDto(dto));

        verify(service).create(payload.getDna());
    }

    @Test
    void createHuman() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTG"});
        Dna mockDna = createDna("9bfed197-f6eb-4910-b0c6-c615b8385bee", payload.getDna(), "20", false);
        DnaDto dto = createDnaDto(mockDna.getId(), mockDna.getDna(), mockDna.getHash(), mockDna.getMutant());
        when(service.create(payload.getDna())).thenReturn(mockDna);

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isForbidden())
                .andExpectAll(assertDnaDto(dto));

        verify(service).create(payload.getDna());
    }

    @Test
    void createIsNotSquare() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTC", "TCACTG"});
        when(service.create(payload.getDna())).thenThrow(new NotSquareException("DNA must be a square table"));

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("DNA must be a square table"));

        verify(service).create(payload.getDna());
    }

    @Test
    void createMutantAlreadyExists() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTC", "TCACTG"});
        when(service.create(payload.getDna())).thenThrow(new MutantAlreadyExistsException("Dna already exists"));

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isConflict())
                .andExpect(status().reason("Dna already exists"));

        verify(service).create(payload.getDna());
    }

    @Test
    void createMutantConflict() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTC", "TCACTG"});
        ConstraintViolationException cause = new ConstraintViolationException(null, null, "idx_dna_hash");
        String message = "Dna already exists";
        DataIntegrityViolationException dataIntegrityViolation = new DataIntegrityViolationException(message, cause);
        when(service.create(payload.getDna())).thenThrow(dataIntegrityViolation);

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isConflict())
                .andExpect(status().reason("Data Integrity Violation"));

        verify(service).create(payload.getDna());
    }

    @Test
    void createMutantConstraintViolationNotByHash() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTC", "TCACTG"});
        ConstraintViolationException cause = new ConstraintViolationException(null, null, "idx_invalid");
        String message = "Dna already exists";
        DataIntegrityViolationException dataIntegrityViolation = new DataIntegrityViolationException(message, cause);
        when(service.create(payload.getDna())).thenThrow(dataIntegrityViolation);

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isConflict())
                .andExpect(status().reason("Data Integrity Violation"));

        verify(service).create(payload.getDna());
    }

    @Test
    void createEmpty() throws Exception {
        PayloadDnaDto payload = createPayloadDnaDto(new String[]{});
        when(service.create(payload.getDna())).thenThrow(new DnaRequiredException("DNA is required"));

        mockMvc.perform(post("/mutant")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("DNA is required"));

        verify(service).create(payload.getDna());
    }

    @Test
    void stats() throws Exception {
        StatsDto mockStats = new StatsDto(100L, 600L);
        when(service.stats()).thenReturn(mockStats);

        mockMvc.perform(get("/mutant/stats")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(assertStatsDto(100, 600, "0.17"));

        verify(service).stats();
    }

}
