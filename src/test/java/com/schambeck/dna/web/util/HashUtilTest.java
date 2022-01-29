package com.schambeck.dna.web.util;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HashUtilTest {

    @Test
    @Order(0)
    void createHashInvalidAlgorithm() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        RuntimeException ex = assertThrows(RuntimeException.class, () -> HashUtil.getInstance("INVALID").hash(dna));
        String expected = "Fail to create new MessageDigest of INVALID";
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

    @Test
    @Order(1)
    void createHash() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        String actual = HashUtil.getInstance().hash(dna);
        String expected = "87DDB4DC78AA028ECD0379B6EF0058435012B13F18D3CA0294ADFA1DC8F3BEED";
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void createHashIOException() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException();
            }
        };
        RuntimeException ex = assertThrows(RuntimeException.class, () -> HashUtil.getInstance().writeBytes(dna, os));
        String expected = "Fail to convert string array to bytes";
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

}
