package africa.vote.SmartVote.datas.dtos.responses;

import africa.vote.SmartVote.datas.enums.Category;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiData {
    private String data;
    private String message;
}
