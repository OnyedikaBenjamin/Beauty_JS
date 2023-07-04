package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VoteRequest {
    @NotBlank(message = "This Field cannot be blank!!!")
    private String candidateId;
}
