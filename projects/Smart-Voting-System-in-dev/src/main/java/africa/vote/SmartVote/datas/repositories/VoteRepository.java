package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, String> {
}
