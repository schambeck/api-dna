package com.schambeck.dna.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.UUID;

public class DnaDto {

    private UUID id;
    private String[] dna;

    public DnaDto() {
    }

    public DnaDto(UUID id, String[] dna) {
        this.id = id;
        this.dna = dna;
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
