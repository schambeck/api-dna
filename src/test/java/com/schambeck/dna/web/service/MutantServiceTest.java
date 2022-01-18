package com.schambeck.dna.web.service;

import com.schambeck.dna.web.exception.DnaRequiredException;
import com.schambeck.dna.web.exception.NotSquareException;
import com.schambeck.dna.web.search.NeighborhoodSearch;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MutantServiceImpl.class, NeighborhoodSearch.class})
class MutantServiceTest {

    @Autowired
    private MutantService service;

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CTGATC;TATTGT;AGAGGG;CCCCTA;TCACTG",
            "TTTTTA;CTGAGC;TATTGT;AGAGGG;CCCCTA;TCACTG"})
    void isMutantHorizontal(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CAGAGC;TTGTGT;ATGTAT;CCGCTT;TCGCTT",
            "CTGAGA;CTGAGC;TTGTGT;ATGTAG;CCGCTA;TCGCTG"})
    void isMutantVertical(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CCAGGC;TACTGT;ATCCAG;CCTCTA;TCGTTG",
            "CTGAGA;CCAGGC;TACTGT;ATCCAG;CCTCTA;TCGTTG"})
    void isMutantDiagonalRight(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;CCAGGC;TAATTT;ATCCAG;CCACTA;TCGTTG",
            "CTGAGA;CCATGC;TAATGT;AACCAG;CCTCTA;TCGTTG"})
    void isMutantDiagonalLeft(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GCGTCA;TCACTG",
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GGGTGA;TCACTG"})
    void isHuman(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertFalse(mutant);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isDnaNullOrEmpty(String[] dna) {
        DnaRequiredException ex = assertThrows(DnaRequiredException.class, () -> service.isMutant(dna));
        String expected = "DNA is required";
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @CsvSource({
            "ATGCG;CAGTGC;TTCTTT;AGAAGG;GCGTCA;TCACTG",
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GGGTGA"})
    void isNotSquare(String input) {
        String[] dna = input.split(";");
        NotSquareException ex = assertThrows(NotSquareException.class, () -> service.isMutant(dna));
        String expected = format("DNA must be a square table %dx%d", dna.length, dna.length);
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

}
