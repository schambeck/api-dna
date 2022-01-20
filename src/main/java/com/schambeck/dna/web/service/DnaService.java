package com.schambeck.dna.web.service;

import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.StatsDto;

public interface DnaService {

    Dna create(String[] dna);

    Dna create(String[] dna, String hash, boolean mutant);

    StatsDto stats();

}
