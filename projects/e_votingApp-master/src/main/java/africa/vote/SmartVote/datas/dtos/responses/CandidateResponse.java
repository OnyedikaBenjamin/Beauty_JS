package africa.vote.SmartVote.datas.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateResponse {
    private String candidateId;
    @NotBlank(message = "This field Cannot be Blank")
    private String candidateName;
}
