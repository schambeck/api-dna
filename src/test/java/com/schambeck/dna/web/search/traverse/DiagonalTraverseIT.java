package com.schambeck.dna.web.search.traverse;

import com.schambeck.dna.web.search.NeighborhoodSearch;
import com.schambeck.dna.web.search.TextSearch;
import com.schambeck.dna.web.search.model.Match;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiagonalTraverseIT {

    private static final int DNA_SIZE = 23;
    private static final int SEQUENCE_COUNT = 4;
    private TextSearch textSearch;

    public static Stream<Arguments> provideDiagonalsRight() {
        List<String[]> dnas = new DiagonalTraverse().executeRight(DNA_SIZE);
        Stream.Builder<Arguments> arguments = Stream.builder();
        dnas.forEach(dna -> arguments.add(Arguments.of((Object) dna)));
        assertEquals(maxMatchesSide(), dnas.size());
        return arguments.build();
    }

    public static Stream<Arguments> provideDiagonalsLeft() {
        List<String[]> dnas = new DiagonalTraverse().executeLeft(DNA_SIZE);
        Stream.Builder<Arguments> arguments = Stream.builder();
        dnas.forEach(dna -> arguments.add(Arguments.of((Object) dna)));
        assertEquals(maxMatchesSide(), dnas.size());
        return arguments.build();
    }

    private static int maxMatchesSide() {
        int rows = DNA_SIZE - SEQUENCE_COUNT + 1;
        int matchesPerRow = DNA_SIZE - SEQUENCE_COUNT + 1;
        return matchesPerRow * rows;
    }

    @BeforeEach
    void init() {
        textSearch = new NeighborhoodSearch();
    }

    @AfterEach
    void tearDownEach() {
        textSearch = null;
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("provideDiagonalsRight")
    void textSearchRight(String[] dna) {
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        Match m = match.get();
        assertEquals("diagonalRight", m.getOrientation());
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("provideDiagonalsLeft")
    void textSearchLeft(String[] dna) {
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        Match m = match.get();
        assertEquals("diagonalLeft", m.getOrientation());
    }

}
