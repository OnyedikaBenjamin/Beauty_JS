package africa.vote.SmartVote.datas.dtos.responses;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreatePollAPIData {
    private String data;
    private String pollId;
}
