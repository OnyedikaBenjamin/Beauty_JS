package africa.vote.SmartVote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmartVoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartVoteApplication.class, args);
	}
}