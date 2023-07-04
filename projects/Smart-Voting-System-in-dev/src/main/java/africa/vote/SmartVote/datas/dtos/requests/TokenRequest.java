package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String token;
    @NotBlank(message = "This field Cannot be Blank")
    private String email;
}
