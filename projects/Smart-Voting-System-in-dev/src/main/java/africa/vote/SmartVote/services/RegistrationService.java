package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.RegistrationRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;

public interface RegistrationService {
    ApiData register(RegistrationRequest registrationRequest);
}
