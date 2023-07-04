package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, String> {

    Optional<Poll> findById(String pollId);
}
