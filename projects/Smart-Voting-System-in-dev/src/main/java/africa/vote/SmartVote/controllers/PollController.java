package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.PollService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/poll/")
public class PollController {
    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping("create")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> createPoll(@Valid @RequestBody CreatePollRequest createPollRequest,
                                        HttpServletRequest request) {

        var data = pollService.createPoll(createPollRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("active")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> activePolls(HttpServletRequest request) {
        var data = pollService.activePolls();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("recent")
    @SecurityRequirement(name = "BearerAuth")

    public ResponseEntity<?> recentPolls(HttpServletRequest request) {
        var data = pollService.recentPolls();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("vote/{poll_id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> vote(@PathVariable("poll_id") String pollId,
                                  @Valid @RequestBody VoteRequest voteRequest,
                                  HttpServletRequest request) {

        var data = pollService.vote(pollId, voteRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("vote/{poll_id}")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> delete(@PathVariable("poll_id") String pollId,
                                  HttpServletRequest request) {

        var data = pollService.deletePoll(pollId);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}