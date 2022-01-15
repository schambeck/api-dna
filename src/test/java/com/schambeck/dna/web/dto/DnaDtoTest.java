package com.schambeck.dna.web.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DnaDtoTest {

    @Test
    void testEquals() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        DnaDto expected = new DnaDto(UUID.fromString("5df74347-2736-4543-a155-5dbcee0b2294"), dna, true);
        DnaDto actual = new DnaDto(UUID.fromString("66b2aad9-3b7d-462a-9640-525f3997057a"), dna, true);
        assertEquals(expected, actual);
    }

    @Test
    void testNotEquals() {
        DnaDto expected = new DnaDto(UUID.fromString("7724d3d6-b2d4-4433-9d1f-9665ddd337aa"), new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"}, true);
        DnaDto actual = new DnaDto(UUID.fromString("72a1009f-95b9-455c-b3c7-5918047136e2"), new String[]{"TTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"}, true);
        assertNotEquals(expected, actual);
    }

    @Test
    void testGetters() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        DnaDto expected = new DnaDto(UUID.fromString("66b2aad9-3b7d-462a-9640-525f3997057a"), dna, true);
        DnaDto actual = new DnaDto(UUID.fromString("66b2aad9-3b7d-462a-9640-525f3997057a"), dna, true);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDna(), actual.getDna());
        assertEquals(expected.isMutant(), actual.isMutant());
    }

    @Test
    void testHashCode() {
        DnaDto expectedDna = new DnaDto();
        expectedDna.setId(UUID.fromString("7bbf973c-680a-48aa-8741-f3c6a342f861"));
        expectedDna.setDna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        expectedDna.setMutant(true);

        DnaDto actualDna = new DnaDto();
        actualDna.setId(UUID.fromString("30d7dd7a-83bc-490d-9710-431e325ac21b"));
        actualDna.setDna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        actualDna.setMutant(true);

        int expected = expectedDna.hashCode();
        int actual = actualDna.hashCode();
        assertEquals(expected, actual);
    }

    @Test
    void testNotEqualHashCode() {
        DnaDto expectedDna = new DnaDto();
        expectedDna.setId(UUID.fromString("7bbf973c-680a-48aa-8741-f3c6a342f861"));
        expectedDna.setDna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        expectedDna.setMutant(true);

        DnaDto actualDna = new DnaDto();
        actualDna.setId(UUID.fromString("30d7dd7a-83bc-490d-9710-431e325ac21b"));
        actualDna.setDna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CTCCTA", "TCACTG"});
        actualDna.setMutant(false);

        int expected = expectedDna.hashCode();
        int actual = actualDna.hashCode();
        assertNotEquals(expected, actual);
    }

}
