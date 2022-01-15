package com.schambeck.dna.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PayloadDnaDtoTest {

    @Test
    void testEquals() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        PayloadDnaDto expected = new PayloadDnaDto(dna);
        PayloadDnaDto actual = new PayloadDnaDto(dna);
        assertEquals(expected, actual);
    }

    @Test
    void testNotEquals() {
        PayloadDnaDto expected = new PayloadDnaDto(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        PayloadDnaDto actual = new PayloadDnaDto(new String[]{"TTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        assertNotEquals(expected, actual);
    }

    @Test
    void testHashCode() {
        int expected = new PayloadDnaDto(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"}).hashCode();
        int actual = new PayloadDnaDto(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"}).hashCode();
        assertEquals(expected, actual);
    }

    @Test
    void testGetters() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        PayloadDnaDto expectedPayload = new PayloadDnaDto();
        expectedPayload.setDna(dna);
        PayloadDnaDto actualPayload = new PayloadDnaDto();
        actualPayload.setDna(dna);
        String[] expected = expectedPayload.getDna();
        String[] actual = actualPayload.getDna();
        assertEquals(expected, actual);
    }

}
