package com.schambeck.dna.web.dto;

import java.util.Arrays;

public class PayloadDnaDto {

    private String[] dna;

    public PayloadDnaDto() {
    }

    public PayloadDnaDto(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayloadDnaDto dnaDto = (PayloadDnaDto) o;
        return Arrays.equals(dna, dnaDto.dna);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dna);
    }

}
