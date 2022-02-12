package com.schambeck.dna.web.service;

import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.StatsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DnaService {

    Dna create(String[] dna);

    Dna create(String[] dna, String hash, boolean mutant);

    Page<Dna> findAll(Pageable pageable);

    StatsDto stats();

}
