package com.schambeck.dna.web.search;

import com.schambeck.dna.web.search.model.Match;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = NeighborhoodSearch.class)
class TextSearchTest {

    @Autowired
    private TextSearch textSearch;

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CTGATC;TATTGT;AGAGGG;CCCCTA;TCACTG",
            "TTTTTA;CTGAGC;TATTGT;AGAGGG;CCCCTA;TCACTG"})
    void isMutantHorizontal(String input) {
        String[] dna = input.split(";");
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        assertEquals("horizontal", match.get().getOrientation());
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CAGAGC;TTGTGT;ATGTAT;CCGCTT;TCGCTT",
            "CTGAGA;CTGAGC;TTGTGT;ATGTAG;CCGCTA;TCGCTG"})
    void isMutantVertical(String input) {
        String[] dna = input.split(";");
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        assertEquals("vertical", match.get().getOrientation());
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CCAGGC;TACTGT;ATCCAG;CCTCTA;TCGTTG",
            "CTGAGA;CCAGGC;TACTGT;ATCCAG;CCTCTA;TCGTTG"})
    void isMutantDiagonalRight(String input) {
        String[] dna = input.split(";");
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        assertEquals("diagonalRight", match.get().getOrientation());
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CCAGGC;TAATTT;ATCCAG;CCACTA;TCGTTG",
            "CTGAGA;CCATGC;TAATGT;AACCAG;CCTCTA;TCGTTG"})
    void isMutantDiagonalLeft(String input) {
        String[] dna = input.split(";");
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        assertEquals("diagonalLeft", match.get().getOrientation());
    }

    @ParameterizedTest
    @CsvSource({
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GCGTCA;TCACTG",
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GGGTGA;TCACTG"})
    void isHuman(String input) {
        String[] dna = input.split(";");
        Optional<Match> match = textSearch.findFirst(dna);
        assertFalse(match.isPresent());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isDnaNullOrEmpty(String[] dna) {
        Optional<Match> match = textSearch.findFirst(dna);
        assertFalse(match.isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "ATGCG;CAGTGC;TTCTTT;AGAAGG;GCGTCA;TCACTG",
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GGGTGA"})
    void isNotSquare(String input) {
        String[] dna = input.split(";");
        Optional<Match> match = textSearch.findFirst(dna);
        assertFalse(match.isPresent());
    }

}
