package vn.edu.iuh.fit.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.entities.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s JOIN s.jobSkills js WHERE s.id NOT IN (SELECT cs.skill.id FROM CandidateSkill cs WHERE cs.candidate.id = :candidateId) group by s order by count(s) desc")
    List<Skill> suggestForCandidate(long candidateId, Pageable pageable);
}