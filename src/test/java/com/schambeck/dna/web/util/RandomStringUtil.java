package com.schambeck.dna.web.util;

public class RandomStringUtil {

    private static final String NO_SEQUENCE_LETTERS = "AAATTTCCC";
    private static final String SEQUENCE_LETTER = "G";
    private static final int SEQUENCE_COUNT = 4;

    public static String[] randomDnaNoSequence(int count) {
        String[] dna = new String[count];
        int lastChar = 0;
        for (int i = 0; i < count; i++) {
            StringBuilder text = new StringBuilder(NO_SEQUENCE_LETTERS.length());
            for (int j = lastChar; j < NO_SEQUENCE_LETTERS.length(); j++) {
                char charAt = NO_SEQUENCE_LETTERS.charAt(j);
                if (text.length() < count) {
                    text.append(charAt);
                    if (j == NO_SEQUENCE_LETTERS.length() - 1) {
                        j = -1;
                    }
                } else {
                    lastChar = j;
                    break;
                }
            }
            dna[i] = text.toString();
        }
        return dna;
    }

    public static String[] randomDnaHorizontalSequence(int count, int startRowSequence, int startColSequence) {
        String[] dna = randomDnaNoSequence(count);
        for (int innerCol = startColSequence; innerCol - startColSequence < SEQUENCE_COUNT; innerCol++) {
            String text = dna[startRowSequence];
            StringBuilder updated = new StringBuilder(text);
            updated.replace(innerCol, innerCol + 1, SEQUENCE_LETTER);
            dna[startRowSequence] = updated.toString();
        }
        return dna;
    }

    public static String[] randomDnaVerticalSequence(int count, int startRowSequence, int startColSequence) {
        String[] dna = randomDnaNoSequence(count);
        for (int row = startRowSequence; row < dna.length; row++) {
            String text = dna[row];
            StringBuilder updated = new StringBuilder(text);
            updated.replace(startColSequence, startColSequence + 1, SEQUENCE_LETTER);
            dna[row] = updated.toString();
        }
        return dna;
    }

    public static String[] randomDnaDiagonalSequenceRight(int count, int startRowSequence, int startColSequence) {
        String[] dna = randomDnaNoSequence(count);
        for (int row = startRowSequence, col = startColSequence; row < dna.length && col < dna.length; row++, col++) {
            String text = dna[row];
            StringBuilder updated = new StringBuilder(text);
            updated.replace(col, col + 1, SEQUENCE_LETTER);
            dna[row] = updated.toString();
        }
        return dna;
    }

    public static String[] randomDnaDiagonalSequenceLeft(int count, int startRowSequence, int startColSequence) {
        String[] dna = randomDnaNoSequence(count);
        for (int row = startRowSequence, col = startColSequence; row > startRowSequence - SEQUENCE_COUNT; row--, col++) {
            String text = dna[row];
            StringBuilder updated = new StringBuilder(text);
            updated.replace(col, col + 1, SEQUENCE_LETTER);
            dna[row] = updated.toString();
        }
        return dna;
    }

}
