package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.RegistrationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/registration/")
public class RegistrationController {
    public final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("register")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationRequest registrationRequest,
                                          HttpServletRequest httpServletRequest) throws MessagingException {

        var createdUser = registrationService.register(registrationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.CREATED)
                .data(createdUser)
                .timestamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}