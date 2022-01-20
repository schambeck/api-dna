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

class HorizontalTraverseIT {

    private static final int DNA_SIZE = 23;
    private static final int SEQUENCE_COUNT = 4;
    private TextSearch textSearch;

    public static Stream<Arguments> provideHorizontals() {
        List<String[]> dnas = new HorizontalTraverse().execute(DNA_SIZE);
        Stream.Builder<Arguments> arguments = Stream.builder();
        dnas.forEach(dna -> arguments.add(Arguments.of((Object) dna)));
        assertEquals(maxMatches(), dnas.size());
        return arguments.build();
    }

    private static int maxMatches() {
        int matchesPerRow = DNA_SIZE - SEQUENCE_COUNT + 1;
        return matchesPerRow * DNA_SIZE;
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
    @MethodSource("provideHorizontals")
    void textSearch(String[] dna) {
        Optional<Match> match = textSearch.findFirst(dna);
        assertTrue(match.isPresent());
        Match m = match.get();
        assertEquals("horizontal", m.getOrientation());
    }

}
