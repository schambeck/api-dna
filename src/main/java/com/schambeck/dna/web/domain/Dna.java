package com.schambeck.dna.web.domain;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@TypeDefs(@TypeDef(name = "string-array", typeClass = StringArrayType.class))
public class Dna {

    @Id
    @GeneratedValue
    private UUID id;

    @Type(type = "string-array")
    @Column(name = "dna", columnDefinition = "text[]")
    @NotEmpty(message = "Dna is mandatory")
    private String[] dna;

    @NotNull(message = "Hash is mandatory")
    private String hash;

    @NotNull(message = "Mutant flag is mandatory")
    private Boolean mutant;

    public Dna() {
    }

    public Dna(String[] dna, String hash, Boolean mutant) {
        this(null, dna, hash, mutant);
    }

    public Dna(UUID id, String[] dna, String hash, Boolean mutant) {
        this.id = id;
        this.dna = dna;
        this.hash = hash;
        this.mutant = mutant;
    }

    public UUID getId() {
        return id;
    }

    public String[] getDna() {
        return dna;
    }

    public String getHash() {
        return hash;
    }

    public Boolean getMutant() {
        return mutant;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setMutant(Boolean mutant) {
        this.mutant = mutant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dna dna = (Dna) o;
        return Objects.equals(id, dna.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
