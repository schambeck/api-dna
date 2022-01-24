package com.schambeck.dna.web.service;

import com.schambeck.dna.web.util.HashUtil;
import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.QueryStatsDto;
import com.schambeck.dna.web.dto.StatsDto;
import com.schambeck.dna.web.exception.MutantAlreadyExistsException;
import com.schambeck.dna.web.repository.DnaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;

import static com.schambeck.dna.web.util.test.DnaUtil.assertDna;
import static com.schambeck.dna.web.util.test.DnaUtil.createDna;
import static com.schambeck.dna.web.util.test.StatsUtil.assertStatsDto;
import static com.schambeck.dna.web.util.test.StatsUtil.createQueryStatsDto;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {DnaServiceImpl.class})
class DnaServiceTest {

    @Autowired
    private DnaService service;

    @MockBean
    private DnaRepository repository;

    @MockBean
    private MutantService mutantService;

    @Test
    void create() {
        Dna entity = createDna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        Dna mockEntity = createDna("18988518-c010-451b-8489-b14d92a0afd8", entity.getDna(), "10", true);
        when(repository.save(entity)).thenReturn(mockEntity);

        Dna created = service.create(entity.getDna(), mockEntity.getHash(), mockEntity.getMutant());
        assertDna(created, mockEntity);
    }

    @Test
    void createMutant() {
        Dna entity = createDna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});
        Dna mockEntity = createDna("18988518-c010-451b-8489-b14d92a0afd8", entity.getDna(), "10", true);
        when(repository.save(entity)).thenReturn(mockEntity);
        String hash = HashUtil.getInstance().hash(entity.getDna());
        when(repository.existsByHash(hash)).thenReturn(false);
        when(mutantService.isMutant(entity.getDna())).thenReturn(true);

        Dna created = service.create(entity.getDna());
        assertDna(created, mockEntity);
    }

    @Test
    void createHuman() {
        Dna entity = createDna(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTG"});
        Dna mockEntity = createDna("18988518-c010-451b-8489-b14d92a0afd8", entity.getDna(), "10", false);
        String hash = HashUtil.getInstance().hash(entity.getDna());
        when(repository.existsByHash(hash)).thenReturn(false);
        when(mutantService.isMutant(entity.getDna())).thenReturn(false);
        when(repository.save(entity)).thenReturn(mockEntity);

        Dna created = service.create(entity.getDna());
        assertDna(created, mockEntity);
    }

    @Test
    void createConflict() {
        Dna dna = createDna(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTG"});
        String message = "Dna already exists";
        String hash = HashUtil.getInstance().hash(dna.getDna());
        when(repository.existsByHash(hash)).thenReturn(false);
        when(repository.save(dna)).thenThrow(new MutantAlreadyExistsException(message));

        MutantAlreadyExistsException ex = assertThrows(MutantAlreadyExistsException.class, () -> service.create(dna.getDna()));

        String expected = "Dna already exists";
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

    @Test
    void createConflictExistsByHash() {
        Dna dna = createDna(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTG"});
        String hash = HashUtil.getInstance().hash(dna.getDna());
        when(repository.existsByHash(hash)).thenReturn(true);

        MutantAlreadyExistsException ex = assertThrows(MutantAlreadyExistsException.class, () -> service.create(dna.getDna()));
        String expected = "Dna already exists";
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

    @Test
    void createConflictNotDataIntegrityViolation() {
        Dna dna = createDna(new String[]{"ATGCGA", "CAGTGC", "TTCTTT", "AGAAGG", "GCGTCA", "TCACTG"});
        String message = "Dna already exists";
        String hash = HashUtil.getInstance().hash(dna.getDna());
        when(repository.existsByHash(hash)).thenReturn(false);
        RuntimeException cause = new RuntimeException();
        DataIntegrityViolationException dataIntegrityViolation = new DataIntegrityViolationException(message, cause);
        when(repository.save(dna)).thenThrow(dataIntegrityViolation);

        DataIntegrityViolationException ex = assertThrows(DataIntegrityViolationException.class, () -> service.create(dna.getDna()));
        String expected = format("%s; nested exception is %s", message, cause.getClass().getName());
        String actual = ex.getMessage();
        assertEquals(actual, expected);
    }

    @Test
    void stats() {
        int countMutantDna = 100;
        int countHumanDna = 600;
        List<QueryStatsDto> queryStats = Arrays.asList(
                createQueryStatsDto(true, countMutantDna),
                createQueryStatsDto(false, countHumanDna));
        when(repository.stats()).thenReturn(queryStats);

        StatsDto found = service.stats();
        assertStatsDto(found, countMutantDna, countHumanDna, "0.17");
    }

}
