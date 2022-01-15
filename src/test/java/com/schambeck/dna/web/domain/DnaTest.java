package com.schambeck.dna.web.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DnaTest {

    @Test
    void testHashCode() {
        String[] dnaExpected = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        int hashExpected = Arrays.hashCode(dnaExpected);
        Dna dnaEntityExpected = new Dna();
        dnaEntityExpected.setDna(dnaExpected);
        dnaEntityExpected.setHash(hashExpected);
        dnaEntityExpected.setMutant(true);

        String[] dnaActual = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        int hashActual = Arrays.hashCode(dnaActual);
        Dna dnaEntityActual = new Dna();
        dnaEntityActual.setDna(dnaActual);
        dnaEntityActual.setHash(hashActual);
        dnaEntityActual.setMutant(true);

        int expected = dnaEntityExpected.hashCode();
        int actual = dnaEntityActual.hashCode();
        assertEquals(expected, actual);
    }

    @Test
    void testHash() {
        String[] dnaExpected = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        int hashExpected = Arrays.hashCode(dnaExpected);
        Dna dnaEntityExpected = new Dna();
        dnaEntityExpected.setDna(dnaExpected);
        dnaEntityExpected.setHash(hashExpected);
        dnaEntityExpected.setMutant(true);

        String[] dnaActual = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        int hashActual = Arrays.hashCode(dnaActual);
        Dna dnaEntityActual = new Dna();
        dnaEntityActual.setDna(dnaActual);
        dnaEntityActual.setHash(hashActual);
        dnaEntityActual.setMutant(true);

        int expected = dnaEntityExpected.getHash();
        int actual = dnaEntityActual.getHash();
        assertEquals(expected, actual);
    }

}