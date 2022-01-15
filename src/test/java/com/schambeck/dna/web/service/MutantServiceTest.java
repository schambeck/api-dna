package com.schambeck.dna.web.service;

import com.schambeck.dna.web.exception.DnaRequiredException;
import com.schambeck.dna.web.exception.NotSquareException;
import com.schambeck.dna.web.search.NeighborhoodSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
            "CTGAGA;CTGAGC;TATTGT;AGAGGG;CCCCTA;TCACTG",
            "TTTTTA;CTGAGC;TATTGT;AGAGGG;CCCCTA;TCACTG",
            "TAAGTAGAAC;GACTCCTTTT;GTTAATACAC;CACACTTCAG;GAACTCCATG;CCCGGTCCGT;TTGGCAACGC;ATCCATAGAC;CATGGAAGTT;GGAAGTACAA"})
    void isMutantHorizontal(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "ATGAGAGAGA;" +
                    "ATGAGCGAGA;" +
                    "AAATGTGAGA;" +
                    "TGGTAGAGAG;" +
                    "TTGAGAGAGA;" +
                    "TTGAGCGAGA;" +
                    "TAATGTGAGA;" +
                    "TGGTAGGAGA;" +
                    "TCGCTAGAGA;" +
                    "TCGCTGGAGA",

            "CTGAGA;" +
                    "CTGAGC;" +
                    "TTGTGT;" +
                    "ATGTAG;" +
                    "CCGCTA;" +
                    "TCGCTG"})
    void isMutantVertical(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;" +
                    "CCAGGC;" +
                    "TACTGT;" +
                    "ATCCAG;" +
                    "CCTCTA;" +
                    "TCGTTG",

            "CTGAGA;" +
                    "CCAGGC;" +
                    "TACTGT;" +
                    "ATCCAG;" +
                    "CCTCTA;" +
                    "TCGTTG"
    })
    void isMutantDiagonalLeftRight(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "CTGAGA;" +
                    "CCAGGC;" +
                    "TAATGT;" +
                    "ATCCAG;" +
                    "CCACTA;" +
                    "TCGTTG",

            "CTGAGA;" +
                    "CCAGGC;" +
                    "TAATGT;" +
                    "AACCAG;" +
                    "CCTCTA;" +
                    "TCGTTG"
    })
    void isMutantDiagonalRightLeft(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertTrue(mutant);
    }

    @ParameterizedTest
    @CsvSource({
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GCGTCA;TCACTG",
            "ATGCGA;CAGTGC;TTCTTT;AGAAGG;GGGTGA;TCACTG"
    })
    void isHuman(String input) {
        String[] dna = input.split(";");
        boolean mutant = service.isMutant(dna);
        assertFalse(mutant);
    }

    @Test
    void isDnaNull() {
        DnaRequiredException ex = assertThrows(DnaRequiredException.class, () -> service.isMutant(null));
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
