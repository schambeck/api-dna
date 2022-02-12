package com.schambeck.dna.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.UUID;

public class DnaDto {

    private UUID id;
    private String[] dna;
    private String hash;
    private Boolean mutant;

    public DnaDto() {
    }

    public DnaDto(UUID id, String[] dna, String hash, Boolean mutant) {
        this.id = id;
        this.dna = dna;
        this.hash = hash;
        this.mutant = mutant;
    }

    public UUID getId() {
        return id;
    }

    void setId(UUID id) {
        this.id = id;
    }

    @JsonIgnore
    public String[] getDna() {
        return dna;
    }

    void setDna(String[] dna) {
        this.dna = dna;
    }

    public String getHash() {
        return hash;
    }

    public Boolean getMutant() {
        return mutant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DnaDto dnaDto = (DnaDto) o;
        return Arrays.equals(dna, dnaDto.dna);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dna);
    }

}
