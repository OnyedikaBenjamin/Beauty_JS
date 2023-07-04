package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCandidateRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String candidateName;
}