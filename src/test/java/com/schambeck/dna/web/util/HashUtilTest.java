package com.schambeck.dna.web.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashUtilTest {

    @Test
    void createHash() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        String actual = HashUtil.getInstance().hash(dna);
        String expected = "2BED6CDF8E1818067352975757C3D39289C522B186A887FB6EB3EFB1CFB82287";
        assertEquals(expected, actual);
    }


}
