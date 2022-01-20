package com.schambeck.dna.web.repository;

import com.schambeck.dna.web.util.HashUtil;
import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.QueryStatsDto;
import com.schambeck.dna.web.util.DnaPostgresqlContainer;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;
import java.util.List;

import static com.schambeck.dna.web.util.test.DnaUtil.assertDna;
import static com.schambeck.dna.web.util.test.DnaUtil.createDna;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class DnaRepositoryTCIT {

    @Autowired
    private DnaRepository repository;

    @ClassRule
    public static PostgreSQLContainer<DnaPostgresqlContainer> postgreSQLContainer = DnaPostgresqlContainer.getInstance();

    @Test
    @Transactional
    void createMutant() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        Dna entity = createDna(dna, HashUtil.getInstance().hash(dna), true);
        Dna created = repository.save(entity);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertDna(created, entity.getDna(), entity.getMutant(), entity.getHash());
    }

    @Test
    @Transactional
    void createHuman() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTG"};
        Dna entity = createDna(dna, HashUtil.getInstance().hash(dna), false);
        Dna created = repository.save(entity);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertDna(created, entity.getDna(), entity.getMutant(), entity.getHash());
    }

    @Test
    @Transactional
    void createConflict() {
        String[] dna = new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTT"};
        Dna entity1 = createDna(dna, HashUtil.getInstance().hash(dna), true);
        repository.save(entity1);
        Dna entity2 = createDna(dna, HashUtil.getInstance().hash(dna), true);
        String message = "could not execute statement; SQL [n/a]; constraint [idx_dna_hash]";
        ConstraintViolationException cause = new ConstraintViolationException(message, null, "idx_dna_hash");
        DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(entity2);
            repository.flush();
        });
        String expected = format("%s; nested exception is %s: could not execute statement", message, cause.getClass().getName());
        String actual = ex.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void existsByHashTrue() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACAG"};
        Dna entity = createDna(dna, HashUtil.getInstance().hash(dna), true);
        Dna created = repository.save(entity);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertDna(created, entity.getDna(), entity.getMutant(), entity.getHash());
        boolean exists = repository.existsByHash(created.getHash());
        assertTrue(exists);
    }

    @Test
    void existsByHashFalse() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        Dna entity = createDna(dna, HashUtil.getInstance().hash(dna), true);
        Dna created = repository.save(entity);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertDna(created, entity.getDna(), entity.getMutant(), entity.getHash());
        boolean exists = repository.existsByHash(created.getHash());
        assertTrue(exists);
        String[] dnaNotExists = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CAACTA", "TCACCC"};
        boolean notExists = repository.existsByHash(HashUtil.getInstance().hash(dnaNotExists));
        assertFalse(notExists);
    }

    @Test
    void stats() {
        String[] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTA"};
        save(dna, true);
        dna = new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCCCTT"};
        save(dna, false);
        dna = new String[]{"ATGCGA", "CAGTGC", "TTCTAT", "AGAAGG", "GCGTCA", "TCACTC"};
        save(dna, false);
        List<QueryStatsDto> stats = repository.stats();
        assertEquals(2, stats.size());
        for (QueryStatsDto stat : stats) {
            if (stat.isMutant()) {
                assertEquals(1, stat.getCount());
            } else {
                assertEquals(2, stat.getCount());
            }
        }
    }

    private void save(String[] dna, boolean mutant) {
        Dna entity = createDna(dna, HashUtil.getInstance().hash(dna), mutant);
        Dna created = repository.save(entity);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertDna(created, entity.getDna(), entity.getMutant(), entity.getHash());
    }

}
