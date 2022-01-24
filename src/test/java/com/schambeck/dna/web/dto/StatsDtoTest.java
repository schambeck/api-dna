package com.schambeck.dna.web.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StatsDtoTest {

    @Test
    void getCountMutantDna() {
        Long expected = 100L;
        Long actual = new StatsDto(100L, 120L).getCountMutantDna();
        assertEquals(expected, actual);
    }

    @Test
    void getCountHumanDna() {
        Long expected = 120L;
        Long actual = new StatsDto(100L, 120L).getCountHumanDna();
        assertEquals(expected, actual);
    }

    @Test
    void getRatio() {
        StatsDto stats = new StatsDto();
        stats.setCountMutantDna(100L);
        stats.setCountHumanDna(600L);
        BigDecimal expected = new BigDecimal("0.17");
        BigDecimal actual = stats.getRatio();
        assertEquals(expected, actual);
    }

    @Test
    void getRatioMutantNull() {
        StatsDto stats = new StatsDto();
        stats.setCountMutantDna(null);
        stats.setCountHumanDna(600L);
        BigDecimal expected = new BigDecimal("0.00");
        BigDecimal actual = stats.getRatio();
        assertEquals(expected, actual);
    }

    @Test
    void getRatioHumanNull() {
        StatsDto stats = new StatsDto();
        stats.setCountMutantDna(100L);
        stats.setCountHumanDna(null);
        BigDecimal expected = new BigDecimal("100.00");
        BigDecimal actual = stats.getRatio();
        assertEquals(expected, actual);
    }

    @Test
    void getRatioNull() {
        StatsDto stats = new StatsDto();
        stats.setCountMutantDna(null);
        stats.setCountHumanDna(null);
        BigDecimal expected = new BigDecimal("0.00");
        BigDecimal actual = stats.getRatio();
        assertEquals(expected, actual);
    }

}