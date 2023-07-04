package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.*;
import africa.vote.SmartVote.datas.models.Poll;

import java.util.List;

public interface PollService {
    CreatePollAPIData createPoll(CreatePollRequest createPollRequest);
    List<RecentPoll> recentPolls();
    List<ActivePoll>activePolls();
    CreatePollAPIData vote(String pollId, VoteRequest voteRequest);
    Poll findPollById(String pollId);
    ApiData deletePoll(String pollId);
}
