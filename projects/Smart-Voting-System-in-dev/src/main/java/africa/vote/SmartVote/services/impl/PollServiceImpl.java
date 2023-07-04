package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.*;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.models.Poll;
import africa.vote.SmartVote.datas.models.Result;
import africa.vote.SmartVote.datas.models.Vote;
import africa.vote.SmartVote.datas.repositories.PollRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static africa.vote.SmartVote.datas.enums.Status.*;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final UserService userService;
    private final ResultService resultService;
    private final VoteService voteService;
    private final CandidateService candidateService;

    private final ZoneId zone = ZoneId.of("GMT+1");

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           UserService userService,
                           ResultService resultService,
                           VoteService voteService,
                           CandidateService candidateService) {
        this.pollRepository = pollRepository;
        this.userService = userService;
        this.resultService = resultService;
        this.voteService = voteService;
        this.candidateService = candidateService;
    }

    @Override
    @Transactional
    public CreatePollAPIData createPoll(CreatePollRequest createPollRequest) {

        List<Candidate> candidateLists = new ArrayList<>();
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId timeZone = ZoneId.of("UTC");

//       "2023-04-01 08:00:00 24hrs"
        LocalDateTime startDateTime = LocalDateTime.parse(createPollRequest
                .getStartDateTime(), formatter).atZone(timeZone).toLocalDateTime();

        LocalDateTime endDateTime = LocalDateTime.parse(createPollRequest
                .getEndDateTime(), formatter).atZone(timeZone).toLocalDateTime();

        if (endDateTime.isBefore(startDateTime))throw new GenericException("End date/time cant be before start date/time");
        if (startDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll start date/time cant be before current date/time");
        if (endDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll End date/time cant be before current date/time");


        //Building Candidates from Poll Request
        for (int i = 0; i <= createPollRequest.getCandidates().size() - 1; i++) {

            Candidate candidate = new Candidate();
            Result result = new Result();
            result.setNoOfVotes(0L);
            var savedResult = resultService.saveResult(result);
            candidate.setName(createPollRequest.getCandidates().get(i).getCandidateName());
            candidate.setResult(savedResult);

            var savedCandidate = candidateService.save(candidate);
            candidateLists.add(savedCandidate);
        }


        Poll poll = Poll.builder()
                .title(createPollRequest.getTitle())
                .question(createPollRequest.getQuestion())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .candidates(candidateLists)
                .category(Category.getCategory(createPollRequest.getCategory()))
                .users(foundUser)
                .build();

        var savedPoll = pollRepository.save(poll);

        return CreatePollAPIData.builder()
                .data("Poll Successfully Created!!! ")
                .pollId(savedPoll.getId())
                .build();
    }

    @Override

    public List<RecentPoll> recentPolls() {
        List<RecentPoll> recentPolls = new ArrayList<>();
        List<CandidateResult> candidateResults;

        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));

        var foundPolls =  pollRepository.findAll()
                .stream()
                .filter(poll -> poll
                        .getEndDateTime()
                        .isBefore(LocalDateTime.now()
                                .atZone(zone).toLocalDateTime())
                )
                .toList();

        for (Poll poll : foundPolls) {
            candidateResults = new ArrayList<>();
            RecentPoll recentPollList = new RecentPoll();
            recentPollList.setTitle(poll.getTitle());
            recentPollList.setQuestion(poll.getQuestion());
            recentPollList.setStartDateTime(poll.getStartDateTime());
            recentPollList.setEndDateTime(poll.getEndDateTime());
            recentPollList.setCategory(poll.getCategory());
            recentPollList.setStatus(CLOSED);

            Poll foundCandidatePoll = pollRepository.findById(poll.getId())
                    .orElseThrow(() -> new GenericException("Invalid Polls"));
            for (Candidate candidate : foundCandidatePoll.getCandidates()) {
                CandidateResult candidateResult = CandidateResult.builder()
                        .candidateName(candidate.getName())
                        .candidateResult(candidate.getResult().getNoOfVotes())
                        .pollId(foundCandidatePoll.getId())
                        .build();
                candidateResults.add(candidateResult);
            }
            recentPollList.setCandidates(candidateResults);
            recentPolls.add(recentPollList);
        }
        return recentPolls;
    }


    @Override
    public List<ActivePoll> activePolls() {

        List<ActivePoll> activePollList = new ArrayList<>();
        List<CandidateResponse> candidateList;

        var userEmail = userService.getUserName();

        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));

        var foundPolls = pollRepository.findAll()
                .stream()
                .filter(poll -> (poll
                        .getStartDateTime()
                        .equals(LocalDateTime.now().atZone(zone).toLocalDateTime())
                || poll
                        .getStartDateTime()
                        .isBefore(LocalDateTime.now().atZone(zone).toLocalDateTime()))
                && poll.getCategory()
                        .equals(foundUser.getCategory())
                && poll.
                        getEndDateTime()
                        .isAfter(LocalDateTime.now().atZone(zone).toLocalDateTime()))
                .toList();

        for (Poll poll : foundPolls) {
            candidateList = new ArrayList<>();
            ActivePoll activePoll = new ActivePoll();
            activePoll.setTitle(poll.getTitle());
            activePoll.setQuestion(poll.getQuestion());
            activePoll.setStartDateTime(poll.getStartDateTime());
            activePoll.setEndDateTime(poll.getEndDateTime());
            activePoll.setCategory(poll.getCategory());
            activePoll.setPollId(poll.getId());
            activePoll.setStatus(ACTIVE);

            Poll foundCandidatePoll = pollRepository.findById(poll.getId())
                    .orElseThrow(() -> new GenericException("Invalid Polls"));

            for (Candidate candidate : foundCandidatePoll.getCandidates()) {
                CandidateResponse candidateResponse = CandidateResponse.builder()
                        .candidateName(candidate.getName())
                        .candidateId(candidate.getId())
                        .build();
                candidateList.add(candidateResponse);
            }
            activePoll.setCandidates(candidateList);
            activePollList.add(activePoll);
        }
        return activePollList;
    }

    @Transactional
    @Override
    public CreatePollAPIData vote(String pollId, VoteRequest voteRequest) {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));

        Poll foundPoll = findPollById(pollId);

        if (!foundPoll.getCandidates().contains(candidateService.findById(
                voteRequest.getCandidateId()
        ))) throw new GenericException("Candidate does not belong to this poll");

        for (Vote vote: voteService.findAllVotes()) {
            boolean votedBefore = vote.getPolls().contains(foundPoll) &&
                    vote.getAppUsers().contains(foundUser)
                    && vote.isVoted();
            if (votedBefore)throw new GenericException("You can't vote twice");
        }
        List<Candidate> foundPollCandidates = foundPoll.getCandidates();


        for (Candidate candidate: foundPollCandidates) {
            if (candidate.getId().equals(voteRequest.getCandidateId())){
                String resultId = candidate.getResult().getId();
                resultService.updateCandidateResult(resultId);
                Vote vote = new Vote();
                vote.getPolls().add(foundPoll);
                vote.getAppUsers().add(foundUser);
                vote.setVoted(true);
                voteService.saveUserVote(vote);
            }
        }
        return CreatePollAPIData.builder()
                .data("You have successfully casted your vote!!! ")
                .pollId(foundPoll.getId())
                .build();
    }

    @Override
    public Poll findPollById(String pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(()-> new GenericException("Poll Id Does not Exist! "));
    }

    @Override
    public ApiData deletePoll(String pollId) {
        var foundPoll = findPollById(pollId);
        pollRepository.delete(foundPoll);
        return ApiData.builder()
                .data("Poll ID Deleted Successfully")
                .build();
    }
}