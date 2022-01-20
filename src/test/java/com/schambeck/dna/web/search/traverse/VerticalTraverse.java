package com.schambeck.dna.web.search.traverse;

import java.util.ArrayList;
import java.util.List;

import static com.schambeck.dna.web.util.RandomStringUtil.randomDnaNoSequence;
import static com.schambeck.dna.web.util.DnaUtil.SEQUENCE_COUNT;

public class VerticalTraverse {

    private static final String SEQUENCE_LETTER = "G";
    private final List<String[]> dnas = new ArrayList<>();

    public List<String[]> execute(int dnaSize) {
        addVerticals(dnaSize);
        return dnas;
    }

    private void addVerticals(int dnaSize) {
        for (int row = 0; row <= dnaSize - SEQUENCE_COUNT; row++) {
            for (int col = 0; col < dnaSize; col++) {
                String[] dna = randomDnaNoSequence(dnaSize);
                for (int innerRow = row; innerRow - row < SEQUENCE_COUNT; innerRow++) {
                    String text = dna[innerRow];
                    StringBuilder updated = new StringBuilder(text);
                    updated.replace(col, col + 1, SEQUENCE_LETTER);
                    dna[innerRow] = updated.toString();
                }
                dnas.add(dna);
            }
        }
    }

}
