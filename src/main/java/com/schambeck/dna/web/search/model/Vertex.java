package com.schambeck.dna.web.search.model;

import java.util.Objects;

public class Vertex {

    private final int row;
    private final int col;

    public Vertex(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return row == vertex.row && col == vertex.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

}
