package com.schambeck.dna.web.search;

import com.schambeck.dna.web.search.model.Match;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;

@Service
public class NeighborhoodSearch implements TextSearch {

    private static final int SEQUENCE_COUNT = 4;

    @Override
    public boolean contains(String[] dna) {
        return findFirst(dna).isPresent();
    }

    @Override
    public Optional<Match> findFirst(String[] dna) {
        if (dna == null || dna.length == 0) {
            return empty();
        }
        for (int row = 0; row < dna.length; row++) {
            String text = dna[row];
            char previousChar = 0;
            int countHorizontal = 0;
            for (int col = 0; col < text.length(); col++) {
                char charHorizontal = text.charAt(col);
                Optional<Match> matchVertical = searchNearVertical(dna, row, col, charHorizontal);
                if (matchVertical.isPresent()) {
                    return matchVertical;
                }
                Optional<Match> matchDiagonal = searchNearDiagonal(dna, row, col, charHorizontal);
                if (matchDiagonal.isPresent()) {
                    return matchDiagonal;
                }
                if (col > 0) {
                    if (charHorizontal != previousChar) {
                        countHorizontal = 0;
                        previousChar = charHorizontal;
                        continue;
                    }
                    countHorizontal++;
                    if (countHorizontal == SEQUENCE_COUNT - 1) {
                        return Optional.of(new Match("horizontal", charHorizontal)
                                .addVertex(row, col - 3)
                                .addVertex(row, col - 2)
                                .addVertex(row, col - 1)
                                .addVertex(row, col));
                    }
                }
                previousChar = charHorizontal;
            }
        }
        return empty();
    }

    private Optional<Match> searchNearVertical(String[] dna, int rowH, int colH, char charH) {
        if (isVerticalInvalid(dna.length, rowH)) {
            return empty();
        }
        for (int row = rowH + 1; row < rowH + SEQUENCE_COUNT - 1; row++) {
            String text = dna[row];
            char charVertical = text.charAt(colH);
            if (charVertical != charH) {
                return empty();
            }
        }
        return Optional.of(new Match("vertical", charH)
                .addVertex(rowH, colH)
                .addVertex(rowH + 1, colH)
                .addVertex(rowH + 2, colH)
                .addVertex(rowH + 3, colH));
    }

    private boolean isVerticalInvalid(int dnaLength, int rowH) {
        int lastRow = dnaLength - SEQUENCE_COUNT;
        return rowH > lastRow;
    }

    private Optional<Match> searchNearDiagonal(String[] dna, int row, int col, char charHorizontal) {
        Optional<Match> matchRight = searchNearDiagonalRight(dna, row, col, charHorizontal);
        if (matchRight.isPresent()) {
            return matchRight;
        }
        return searchNearDiagonalLeft(dna, row, col, charHorizontal);
    }

    private Optional<Match> searchNearDiagonalRight(String[] dna, int rowH, int colH, char charH) {
        if (isDiagonalRightInvalid(dna.length, rowH, colH)) {
            return empty();
        }
        for (int row = rowH + 1, col = colH + 1; row <= rowH + SEQUENCE_COUNT - 1; row++, col++) {
            String text = dna[row];
            char charDiagonal = text.charAt(col);
            if (charDiagonal != charH) {
                return empty();
            }
        }
        return Optional.of(new Match("diagonalRight", charH)
                .addVertex(rowH, colH)
                .addVertex(rowH + 1, colH + 1)
                .addVertex(rowH + 2, colH + 2)
                .addVertex(rowH + 3, colH + 3));
    }

    private boolean isDiagonalRightInvalid(int dnaLength, int rowH, int colH) {
        int lastRow = dnaLength - SEQUENCE_COUNT;
        int lastCol = dnaLength - SEQUENCE_COUNT;
        return rowH > lastRow || colH > lastCol;
    }

    private Optional<Match> searchNearDiagonalLeft(String[] dna, int rowH, int colH, char charH) {
        if (isDiagonalLeftInvalid(dna.length, rowH, colH)) {
            return empty();
        }
        for (int row = rowH + 1, col = colH - 1; row <= rowH + SEQUENCE_COUNT - 1; row++, col--) {
            String text = dna[row];
            char charDiagonal = text.charAt(col);
            if (charDiagonal != charH) {
                return empty();
            }
        }
        return Optional.of(new Match("diagonalLeft", charH)
                .addVertex(rowH, colH)
                .addVertex(rowH + 1, colH - 1)
                .addVertex(rowH + 2, colH - 2)
                .addVertex(rowH + 3, colH - 3)
        );
    }

    private boolean isDiagonalLeftInvalid(int dnaLenght, int rowH, int colH) {
        int lastRow = dnaLenght - SEQUENCE_COUNT;
        int firstCol = SEQUENCE_COUNT - 1;
        return rowH > lastRow || colH < firstCol;
    }

}
