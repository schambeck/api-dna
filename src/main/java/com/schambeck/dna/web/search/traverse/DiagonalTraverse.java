package com.schambeck.dna.web.search.traverse;

import java.util.ArrayList;
import java.util.List;

import static com.schambeck.dna.notcovered.util.RandomStringUtil.randomDnaNoSequence;
import static com.schambeck.dna.web.util.DnaUtil.SEQUENCE_COUNT;

public class DiagonalTraverse {

    private static final String SEQUENCE_LETTER = "G";
    private final List<String[]> dnas = new ArrayList<>();

    public List<String[]> executeRight(int dnaSize) {
        addDiagonalsRight(dnaSize);
        return dnas;
    }

    public List<String[]> executeLeft(int dnaSize) {
        addDiagonalsLeft(dnaSize);
        return dnas;
    }

    private void diagonalsRight(int dnaSize, int row, int col) {
        String[] dna = randomDnaNoSequence(dnaSize);
        for (int i = 0, innerRow = row, innerCol = col; i < SEQUENCE_COUNT; i++, innerRow++, innerCol++) {
            String text = dna[innerRow];
            StringBuilder updated = new StringBuilder(text);
            updated.replace(innerCol, innerCol + 1, SEQUENCE_LETTER);
            dna[innerRow] = updated.toString();
        }
        dnas.add(dna);
    }

    private void diagonalsLeft(int dnaSize, int row, int col) {
        String[] dna = randomDnaNoSequence(dnaSize);
        for (int i = 0, innerRow = row, innerCol = col; i < SEQUENCE_COUNT; i++, innerRow++, innerCol--) {
            String text = dna[innerRow];
            StringBuilder updated = new StringBuilder(text);
            updated.replace(innerCol, innerCol + 1, SEQUENCE_LETTER);
            dna[innerRow] = updated.toString();
        }
        dnas.add(dna);
    }

    public void addDiagonalsRight(int dnaSize) {
        int lastRow = dnaSize - SEQUENCE_COUNT;
        int lastCol = dnaSize - 1;
        int firstColRight = 0;
        int lastColRight = dnaSize - SEQUENCE_COUNT;
        for (int row = 0; row <= lastRow; row++) {
            for (int col = 0; col <= lastCol; col++) {
                if (col >= firstColRight && col <= lastColRight) {
                    diagonalsRight(dnaSize, row, col);
                }
            }
        }
    }

    private void addDiagonalsLeft(int dnaSize) {
        int lastRow = dnaSize - SEQUENCE_COUNT;
        int lastCol = dnaSize - 1;
        int firstColLeft = SEQUENCE_COUNT - 1;
        int lastColLeft = dnaSize - 1;
        for (int row = 0; row <= lastRow; row++) {
            for (int col = 0; col <= lastCol; col++) {
                if (col >= firstColLeft && col <= lastColLeft) {
                    diagonalsLeft(dnaSize, row, col);
                }
            }
        }
    }

}
