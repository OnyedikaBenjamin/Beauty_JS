package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, String> {
}
