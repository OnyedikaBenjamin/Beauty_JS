package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreatePollRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String title;
    @NotBlank(message = "This field Cannot be Blank")
    private String question;
    @NotBlank(message = "This field Cannot be Blank")
    private String startDateTime;
    @NotBlank(message = "This field Cannot be Blank")
    private String endDateTime;
    @NotBlank(message = "This field Cannot be Blank")
    private String category;
    private List<CreateCandidateRequest> candidates;
}