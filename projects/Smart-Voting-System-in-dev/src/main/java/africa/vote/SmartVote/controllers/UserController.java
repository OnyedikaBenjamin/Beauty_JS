package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.PollService;
import africa.vote.SmartVote.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;}


    @PostMapping("create")
    public ResponseEntity<?> createUser(@Valid @RequestBody TokenRequest tokenRequest,
                                        HttpServletRequest request) {
        var data = userService.createAccount(tokenRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("resend")
    public ResponseEntity<?> resendToken(@Valid @RequestBody ResendTokenRequest tokenRequest,
                                         HttpServletRequest request){

        var data = userService.sendOTP(tokenRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
                                            HttpServletRequest httpServletRequest){
        var data = userService.authenticate(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("verify")
    public ResponseEntity<?> verify(@Valid @RequestBody TokenRequest tokenRequest,
                                   HttpServletRequest httpServletRequest){
        var data = userService.tokenVerification(tokenRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("time")
    public ResponseEntity<?> time(HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .data(LocalDateTime.now().atZone(ZoneId.of("GMT+5")).toLocalDateTime())
                .timestamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}