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

class VerticalTraverseIT {

    private static final int DNA_SIZE = 23;
    private static final int SEQUENCE_COUNT = 4;
    private TextSearch textSearch;

    public static Stream<Arguments> provideVerticals() {
        List<String[]> dnas = new VerticalTraverse().execute(DNA_SIZE);
        Stream.Builder<Arguments> arguments = Stream.builder();
        dnas.forEach(dna -> arguments.add(Arguments.of((Object) dna)));
        assertEquals(maxMatches(), dnas.size());
        return arguments.build();
    }

    private static int maxMatches() {
        int rows = DNA_SIZE - SEQUENCE_COUNT + 1;
        return DNA_SIZE * rows;
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
    @MethodSource("provideVerticals")
    void textSearch(String[] dna) {
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        Match m = match.get();
        assertEquals("vertical", m.getOrientation());
    }

}
