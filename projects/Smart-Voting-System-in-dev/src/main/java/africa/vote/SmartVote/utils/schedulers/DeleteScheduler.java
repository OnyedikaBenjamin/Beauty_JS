package africa.vote.SmartVote.utils.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DeleteScheduler {

    private final AppScheduler scheduled;


    // Clear all unverified appUsers after 5 days
    @Scheduled(cron = "0 0 0 */5 * *")
    public void deleteUnverifiedUser(){
        scheduled.deleteUnverifiedUsers();
    }

    // Clear unused token after 2 days
    @Scheduled(cron = "0 0 0 */2 * *")
    public void deleteUnconfirmedToken(){
       scheduled.deleteUnconfirmedToken();
    }
}
