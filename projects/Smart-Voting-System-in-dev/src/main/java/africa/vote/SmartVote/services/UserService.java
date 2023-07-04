package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.LoginRequest;
import africa.vote.SmartVote.datas.dtos.requests.ResendTokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.UpdateUserRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.dtos.responses.LoginData;
import africa.vote.SmartVote.datas.models.AppUser;

import java.util.Optional;

public interface UserService {
    void saveUser(AppUser appUser);
    ApiData createAccount(TokenRequest tokenRequest);
    ApiData tokenVerification(TokenRequest tokenRequest);
    Optional<AppUser> findByEmailIgnoreCase(String email);
    ApiData sendOTP(ResendTokenRequest tokenRequest);
    LoginData authenticate(LoginRequest request);
    Optional<AppUser> getById(String userId);
    String getUserName();
    ApiData deleteUser();
    ApiData updateAppUser(UpdateUserRequest userRequest);
}

