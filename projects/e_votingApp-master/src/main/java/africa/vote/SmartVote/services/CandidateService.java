package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.responses.CandidateResult;
import africa.vote.SmartVote.datas.models.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    List<CandidateResult> findAllCandidatesResultOfAPoll(String pollId);
    Candidate save(Candidate candidate);
    Candidate findById(String candidateId);
}
