package com.schambeck.dna.web.domain;

import com.schambeck.dna.web.util.HashUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DnaTest {

    @Test
    void testHashCode() {
        String[] dnaExpected = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        String hashExpected = HashUtil.getInstance().hash(dnaExpected);
        Dna dnaEntityExpected = new Dna();
        dnaEntityExpected.setDna(dnaExpected);
        dnaEntityExpected.setHash(hashExpected);
        dnaEntityExpected.setMutant(true);

        String[] dnaActual = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        String hashActual = HashUtil.getInstance().hash(dnaActual);
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
        String hashExpected = HashUtil.getInstance().hash(dnaExpected);
        Dna dnaEntityExpected = new Dna();
        dnaEntityExpected.setDna(dnaExpected);
        dnaEntityExpected.setHash(hashExpected);
        dnaEntityExpected.setMutant(true);

        String[] dnaActual = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        String hashActual = HashUtil.getInstance().hash(dnaActual);
        Dna dnaEntityActual = new Dna();
        dnaEntityActual.setDna(dnaActual);
        dnaEntityActual.setHash(hashActual);
        dnaEntityActual.setMutant(true);

        String expected = dnaEntityExpected.getHash();
        String actual = dnaEntityActual.getHash();
        assertEquals(expected, actual);
    }

}