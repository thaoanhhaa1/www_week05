package vn.edu.iuh.fit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.entities.Candidate;
import vn.edu.iuh.fit.repositories.CandidateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServices {
    private final CandidateRepository candidateRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public CandidateServices(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public boolean save(Candidate candidate) {
        try {
            candidateRepository.save(candidate);

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    public Optional<Candidate> findById(long id) {
        return candidateRepository.findById(id);
    }

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Optional<Boolean> update(Candidate candidate) {
        if (!exists(candidate))
            return Optional.empty();

        try {
            candidateRepository.save(candidate);
            return Optional.of(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Optional.of(false);
    }

    public Optional<Boolean> delete(long id) {
        if (!exists(id))
            return Optional.empty();

        try {
            candidateRepository.deleteById(id);
            return Optional.of(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Optional.of(false);
    }

    private boolean exists(long id) {
        return candidateRepository.findById(id).isPresent();
    }

    private boolean exists(Candidate candidate) {
        return exists(candidate.getId());
    }

    public Optional<Candidate> login(String email) {
        return candidateRepository.findByEmail(email);
    }
}
