package africa.vote.SmartVote.utils;

import java.security.SecureRandom;

public class TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generaToken(){
       return generateRandomNumber().toString();
    }

    private static Integer generateRandomNumber(){
        return 1000 + secureRandom.nextInt(999);
    }
}
