package com.schambeck.dna.web.search.model;

import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private static final int SEQUENCE_LETTER_SIZE = 4;

    @Test
    void addVertex() {
        new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
    }

    @Test
    void getVertices() {
        com.schambeck.dna.web.search.model.Match match = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
        int expected = 4;
        int actual = match.getVertices().size();
        assertEquals(expected, actual);
    }

    @Test
    void addVertexMaxSize() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3)
                .addVertex(0, 4));
        String expected = format("Maximum vertices per match is %d", SEQUENCE_LETTER_SIZE);
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

    @Test
    void getOrientation() {
        com.schambeck.dna.web.search.model.Match match = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
        String expected = "horizontal";
        String actual = match.getOrientation();
        assertEquals(expected, actual);
    }

    @Test
    void getLetter() {
        com.schambeck.dna.web.search.model.Match match = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
        char expected = 'G';
        char actual = match.getLetter();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        com.schambeck.dna.web.search.model.Match expected = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
        com.schambeck.dna.web.search.model.Match actual = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
        assertEquals(expected, actual);
    }

    @Test
    void testNotEquals() {
        com.schambeck.dna.web.search.model.Match expected = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3);
        com.schambeck.dna.web.search.model.Match actual = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 4);
        assertNotEquals(expected, actual);
    }

    @Test
    void testHashCode() {
        int expected = new com.schambeck.dna.web.search.model.Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3).hashCode();
        int actual = new Match("horizontal", 'G')
                .addVertex(0, 0)
                .addVertex(0, 1)
                .addVertex(0, 2)
                .addVertex(0, 3).hashCode();
        assertEquals(expected, actual);
    }


}