package com.schambeck.dna.web.search.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VertexTest {

    @Test
    void getRow() {
        com.schambeck.dna.web.search.model.Vertex vertex = new com.schambeck.dna.web.search.model.Vertex(2, 4);
        int expected = 2;
        int actual = vertex.getRow();
        assertEquals(expected, actual);
    }

    @Test
    void getCol() {
        com.schambeck.dna.web.search.model.Vertex vertex = new com.schambeck.dna.web.search.model.Vertex(13, 16);
        int expected = 16;
        int actual = vertex.getCol();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        com.schambeck.dna.web.search.model.Vertex expected = new com.schambeck.dna.web.search.model.Vertex(20, 30);
        com.schambeck.dna.web.search.model.Vertex actual = new com.schambeck.dna.web.search.model.Vertex(20, 30);
        assertEquals(expected, actual);
    }

    @Test
    void testNotEquals() {
        com.schambeck.dna.web.search.model.Vertex expected = new com.schambeck.dna.web.search.model.Vertex(20, 30);
        com.schambeck.dna.web.search.model.Vertex actual = new com.schambeck.dna.web.search.model.Vertex(40, 60);
        assertNotEquals(expected, actual);
    }

    @Test
    void testHashCode() {
        int expected = new com.schambeck.dna.web.search.model.Vertex(33, 66).hashCode();
        int actual = new Vertex(33, 66).hashCode();
        assertEquals(expected, actual);
    }

}