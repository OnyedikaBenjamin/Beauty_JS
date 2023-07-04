package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResendTokenRequest {
    @NotBlank(message = "This field Cannot be Blank")
    @Email(message = "This field requires a valid email address")
    private String email;
}
