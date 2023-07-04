package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.models.Result;

public interface ResultService {
    void updateCandidateResult(String resultId);
    Result saveResult(Result result);
}
