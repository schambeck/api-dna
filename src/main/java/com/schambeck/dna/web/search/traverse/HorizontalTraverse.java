package com.schambeck.dna.web.search.traverse;

import java.util.ArrayList;
import java.util.List;

import static com.schambeck.dna.notcovered.util.RandomStringUtil.randomDnaNoSequence;
import static com.schambeck.dna.web.util.DnaUtil.SEQUENCE_COUNT;

public class HorizontalTraverse {

    private static final String SEQUENCE_LETTER = "G";
    private final List<String[]> dnas = new ArrayList<>();

    public List<String[]> execute(int dnaSize) {
        addHorizontals(dnaSize);
        return dnas;
    }

    private void addHorizontals(int dnaSize) {
        for (int row = 0; row < dnaSize; row++) {
            for (int col = 0; col <= dnaSize - SEQUENCE_COUNT; col++) {
                String[] dna = randomDnaNoSequence(dnaSize);
                for (int innerCol = col; innerCol - col < SEQUENCE_COUNT; innerCol++) {
                    String text = dna[row];
                    StringBuilder updated = new StringBuilder(text);
                    updated.replace(innerCol, innerCol + 1, SEQUENCE_LETTER);
                    dna[row] = updated.toString();
                }
                dnas.add(dna);
            }
        }
    }

}
