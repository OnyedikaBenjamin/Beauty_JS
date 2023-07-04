package africa.vote.SmartVote.exeptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}