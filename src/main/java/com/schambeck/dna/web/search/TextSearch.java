package com.schambeck.dna.web.search;

import com.schambeck.dna.web.search.model.Match;

import java.util.Optional;

public interface TextSearch {

    boolean contains(String[] dna);

    Optional<Match> findFirst(String[] dna);

}
