package com.schambeck.dna.web.repository;

import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.QueryStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

public interface DnaRepository extends JpaRepository<Dna, UUID> {

    @Query("SELECT new com.schambeck.dna.web.dto.QueryStatsDto(d.mutant, COUNT(d.id)) " +
            "FROM Dna d " +
            "GROUP BY d.mutant")
    @Transactional(propagation = SUPPORTS, readOnly = true)
    List<QueryStatsDto> stats();

    @Transactional(propagation = SUPPORTS, readOnly = true)
    boolean existsByHash(int hash);

}
