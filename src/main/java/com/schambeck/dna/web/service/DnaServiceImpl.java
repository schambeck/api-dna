package com.schambeck.dna.web.service;

import com.schambeck.dna.web.util.HashUtil;
import com.schambeck.dna.web.domain.Dna;
import com.schambeck.dna.web.dto.QueryStatsDto;
import com.schambeck.dna.web.dto.StatsDto;
import com.schambeck.dna.web.exception.MutantAlreadyExistsException;
import com.schambeck.dna.web.repository.DnaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DnaServiceImpl implements DnaService {

    private final DnaRepository repository;
    private final MutantService mutantService;

    public DnaServiceImpl(DnaRepository repository, MutantService mutantService) {
        this.repository = repository;
        this.mutantService = mutantService;
    }

    @Override
    public Dna create(String[] dna) {
        String hash = HashUtil.getInstance().hash(dna);
        boolean exists = repository.existsByHash(hash);
        if (exists) {
            throw new MutantAlreadyExistsException("Dna already exists");
        }
        boolean mutant = mutantService.isMutant(dna);
        return create(dna, hash, mutant);
    }

    @Override
    @Transactional
    public Dna create(String[] dna, String hash, boolean mutant) {
        Dna entity = new Dna(dna, hash, mutant);
        return repository.save(entity);
    }

    @Override
    public Page<Dna> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Dna> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public StatsDto stats() {
        List<QueryStatsDto> list = repository.stats();
        StatsDto stats = new StatsDto();
        for (QueryStatsDto query : list) {
            if (query.isMutant()) {
                stats.setCountMutantDna(query.getCount());
            } else {
                stats.setCountHumanDna(query.getCount());
            }
        }
        return stats;
    }

}
