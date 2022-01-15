package com.schambeck.dna.web.service;

import com.schambeck.dna.web.exception.DnaRequiredException;
import com.schambeck.dna.web.exception.NotSquareException;
import com.schambeck.dna.web.search.TextSearch;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static java.lang.String.format;

@Service
public class MutantServiceImpl implements MutantService {

    private final TextSearch textSearch;

    public MutantServiceImpl(TextSearch textSearch) {
        this.textSearch = textSearch;
    }

    @Override
    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new DnaRequiredException("DNA is required");
        }
        validateSquare(dna);
        return textSearch.search(dna);
    }

    private void validateSquare(String[] dna) {
        int rows = dna.length;
        boolean anyMatch = Arrays.stream(dna).anyMatch(p -> p.length() != rows);
        if (anyMatch) {
            throw new NotSquareException(format("DNA must be a square table %dx%d", rows, rows));
        }
    }

}
