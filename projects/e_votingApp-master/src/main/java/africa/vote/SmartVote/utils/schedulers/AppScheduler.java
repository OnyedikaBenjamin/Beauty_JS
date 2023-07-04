package africa.vote.SmartVote.utils.schedulers;

import africa.vote.SmartVote.datas.repositories.TokenRepository;
import africa.vote.SmartVote.datas.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static africa.vote.SmartVote.datas.enums.Status.UNVERIFIED;


@Component
public class AppScheduler {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AppScheduler(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public void deleteUnverifiedUsers() {
        userRepository.deleteUnverifiedUsers(UNVERIFIED);
    }

    public void deleteUnconfirmedToken() {
        tokenRepository.deleteUnconfirmedToken();
    }
}
