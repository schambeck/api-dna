package com.schambeck.dna.web.dto;

public class QueryStatsDto {

    private final Boolean mutant;
    private final Long count;

    public QueryStatsDto(Boolean mutant, Long count) {
        this.mutant = mutant;
        this.count = count;
    }

    public Boolean isMutant() {
        return mutant;
    }

    public Long getCount() {
        return count;
    }

}
