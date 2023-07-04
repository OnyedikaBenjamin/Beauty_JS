package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, String> {
}
