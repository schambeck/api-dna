package com.schambeck.dna.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class StatsDto {

    private static final int SCALE = 2;
    private static final BigDecimal ZERO = new BigDecimal("0.00");
    private static final BigDecimal HUNDRED = new BigDecimal("100.00");

    @JsonProperty("count_mutant_dna")
    private Long countMutantDna;

    @JsonProperty("count_human_dna")
    private Long countHumanDna;

    public StatsDto() {
    }

    public StatsDto(Long countMutantDna, Long countHumanDna) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
    }

    public Long getCountMutantDna() {
        return countMutantDna;
    }

    public Long getCountHumanDna() {
        return countHumanDna;
    }

    public BigDecimal getRatio() {
        if (countMutantDna == null || countMutantDna == 0) {
            return ZERO;
        }
        if (countHumanDna == null || countHumanDna == 0) {
            return HUNDRED;
        }
        return new BigDecimal(countMutantDna).divide(new BigDecimal(countHumanDna), SCALE, HALF_UP);
    }

    public void setCountMutantDna(Long countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    public void setCountHumanDna(Long countHumanDna) {
        this.countHumanDna = countHumanDna;
    }

}
