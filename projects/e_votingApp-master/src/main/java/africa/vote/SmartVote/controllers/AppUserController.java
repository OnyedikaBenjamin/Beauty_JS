package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.UpdateUserRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/welcome/")
public class AppUserController {

    public final UserService userService;

    public AppUserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("update")
    public ResponseEntity<?> updateAppUser(@RequestBody UpdateUserRequest userRequest, HttpServletRequest request){

        var data = userService.updateAppUser(userRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){

        var data = userService.deleteUser();
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
