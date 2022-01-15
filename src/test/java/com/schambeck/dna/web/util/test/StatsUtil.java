package com.schambeck.dna.web.util.test;

import com.schambeck.dna.web.dto.QueryStatsDto;
import com.schambeck.dna.web.dto.StatsDto;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public final class StatsUtil {

    private StatsUtil() {
    }

    public static QueryStatsDto createQueryStatsDto(boolean mutant, long count) {
        return new QueryStatsDto(mutant, count);
    }

    public static void assertStatsDto(StatsDto stats, long countMutantDna, long countHumanDna, String ratio) {
        assertEquals(countMutantDna, stats.getCountMutantDna());
        assertEquals(countHumanDna, stats.getCountHumanDna());
        assertEquals(ratio, stats.getRatio().toString());
    }

    public static ResultMatcher[] assertStatsDto(long countMutantDna, long countHumanDna, String ratio) {
        return new ResultMatcher[] {
                jsonPath("$.count_mutant_dna").value(countMutantDna),
                jsonPath("$.count_human_dna").value(countHumanDna),
                jsonPath("$.ratio").value(ratio)
        };
    }

}