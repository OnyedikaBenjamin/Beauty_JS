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
    public CreatePollAPIData createPoll(CreatePollRequest createPollRequest) {
        List<Candidate> candidateLists = new ArrayList<>();
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//       "2023-04-01 08:00:00 24hrs"
        LocalDateTime startDateTime = LocalDateTime.parse(createPollRequest.getStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(createPollRequest.getEndDateTime(), formatter);

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

        if (endDateTime.isBefore(startDateTime))throw new GenericException("End date/time cant be before start date/time");
        if (startDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll start date/time cant be before current date/time");
        if (endDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll End date/time cant be before current date/time");

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
                        .isBefore(LocalDateTime.now())
//                        && poll.getCategory().equals(foundUser.getCategory())
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
        // TODO: 3/7/2023 As per your implementation to create a poll, a user can create poll for other cohort,
        //  but your implementation for finding all active poll, its limiting the search to only his cohorts
        //  Would a user from another cohort be able to participate for a poll outside his cohort,
        //  Since when the poll is created your implementations allows for them to input the cohort the poll
        //  is meant for I commented out the part where you are getting poll by Cohort

        List<ActivePoll> activePollList = new ArrayList<>();
        List<CandidateResponse> candidateList;

        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));

        var foundPolls = pollRepository.findAll()
                .stream()
                .filter(poll -> (poll
                        .getStartDateTime()
                        .equals(LocalDateTime.now())
                || poll
                        .getStartDateTime()
                        .isBefore(LocalDateTime.now()))
//                && poll.getCategory()
//                        .equals(foundUser.getCategory())
                && poll.
                        getEndDateTime()
                        .isAfter(LocalDateTime.now()))
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
        ))) throw new GenericException("Candidate doesnt belong to this poll");


        // TODO: 3/7/2023 Associate Yusuf kabir
        //  validations to ensure voting has commenced I dont know why it isn't working with the below code, please review


//        if(foundPoll.getStartDateTime().isBefore(LocalDateTime.now())) throw new GenericException("Voting for the Category is not Started Yet...");
//        if(foundPoll.getEndDateTime().isAfter(LocalDateTime.now())) throw new GenericException("Voting for the Category has ended ...");

        // TODO: 3/7/2023 When casting a vote, I'm can submit more than one vote from a pollId and candidateId not from the same polls
        // TODO: 3/7/2023 validations to check that the candidateId and pollId are in the same poll before poll submissions
        // TODO: 3/7/2023 The results still seems intacts but I just think we should make validations
        //  {
        //  "pollId": "a2ca0345-3a8f-4a91-874a-623a7a57a4d9", This is a poll Id from a different Poll
        //  "candidateId": "b201f920-ce4b-4670-9f81-c8c93287a02f" This is a candidateId from a different Poll
        //  }

        for (Vote vote: voteService.findAllVotes()) {
            boolean votedBefore = vote.getPolls().contains(foundPoll) && vote.getAppUsers().contains(foundUser) && vote.isVoted();
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