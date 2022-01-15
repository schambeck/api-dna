package com.schambeck.dna.web.search.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class Match {

    private static final int SEQUENCE_LETTER_SIZE = 4;
    private final String orientation;
    private final char letter;
    private final List<Vertex> vertices;

    public Match(String orientation, char letter) {
        this.orientation = orientation;
        this.letter = letter;
        this.vertices = new ArrayList<>();
    }

    public Match addVertex(int row, int col) {
        if (vertices.size() == SEQUENCE_LETTER_SIZE) {
            throw new RuntimeException(format("Maximum vertices per match is %d", SEQUENCE_LETTER_SIZE));
        }
        vertices.add(new Vertex(row, col));
        return this;
    }

    public String getOrientation() {
        return orientation;
    }

    public char getLetter() {
        return letter;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(vertices, match.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

}
