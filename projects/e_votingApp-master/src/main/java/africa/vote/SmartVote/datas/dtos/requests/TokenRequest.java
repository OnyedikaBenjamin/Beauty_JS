package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String token;
    @NotBlank(message = "This field Cannot be Blank")
    private String email;
}
