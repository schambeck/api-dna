package com.schambeck.dna.web.util.test;

import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.DnaDto;
import com.schambeck.dna.web.dto.PayloadDnaDto;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public final class DnaUtil {

    private DnaUtil() {
    }

    public static PayloadDnaDto createPayloadDnaDto(String[] dna) {
        return new PayloadDnaDto(dna);
    }

    public static Dna createDna(String[] dna) {
        return createDna(dna, null, null);
    }

    public static Dna createDna(String[] dna, String hash, Boolean mutant) {
        return createDna(null, dna, hash, mutant);
    }

    public static Dna createDna(String id, String[] dna, String hash, Boolean mutant) {
        UUID uuid = id == null ? null : UUID.fromString(id);
        return new Dna(uuid, dna, hash, mutant);
    }

    public static DnaDto createDnaDto(UUID id, String[] dna) {
        return new DnaDto(id, dna);
    }

    public static ResultMatcher[] assertDnaDto(DnaDto dna) {
        return new ResultMatcher[] {
                jsonPath("$.id").value(dna.getId().toString())
        };
    }

    public static void assertDna(Dna dnaEntity, String[] dna, Boolean mutant, String hash) {
        assertDna(dnaEntity, null, dna, mutant, hash);
    }

    public static void assertDna(Dna dnaEntity, String id, String[] dna, Boolean mutant, String hash) {
        UUID uuid = null;
        if (id != null) {
            uuid = UUID.fromString(id);
        }
        Dna mock = new Dna(uuid, dna, hash, mutant);
        assertDna(dnaEntity, mock);
    }

    public static void assertDna(Dna entity, Dna mockEntity) {
        if (mockEntity.getId() != null) {
            assertEquals(mockEntity.getId(), entity.getId());
        }
        assertArrayEquals(mockEntity.getDna(), entity.getDna());
        assertEquals(mockEntity.getMutant(), entity.getMutant());
        assertEquals(mockEntity.getHash(), entity.getHash());
    }

}
