package com.schambeck.dna.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueryStatsDtoTest {

    @Test
    void isMutant() {
        assertTrue(new QueryStatsDto(true, 100L).isMutant());
    }

    @Test
    void getCount() {
        Long expected = 100L;
        Long actual = new QueryStatsDto(true, 100L).getCount();
        assertEquals(expected, actual);
    }

}
