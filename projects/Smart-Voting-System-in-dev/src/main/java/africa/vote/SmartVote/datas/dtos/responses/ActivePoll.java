package africa.vote.SmartVote.datas.dtos.responses;

import africa.vote.SmartVote.datas.dtos.requests.CreateCandidateRequest;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivePoll {
    private String pollId;
    private String title;
    private String question;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd || HH:mm")
    private LocalDateTime startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd || HH:mm")
    private LocalDateTime endDateTime;
    private Category category;
    private Status status;
    private List<CandidateResponse> candidates;
}
