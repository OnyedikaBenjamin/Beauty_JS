package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String password;
    @Email(message = "This field requires a valid email address")
    private String email;
}
