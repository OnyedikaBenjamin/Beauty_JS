package africa.vote.SmartVote.datas.dtos.responses;


import africa.vote.SmartVote.datas.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateResult {
    private String candidateName;
    private String candidateImageURL;
    private Long candidateResult;
    private String pollId;
}