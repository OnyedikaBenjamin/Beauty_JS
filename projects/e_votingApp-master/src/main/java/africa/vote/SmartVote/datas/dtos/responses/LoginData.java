package africa.vote.SmartVote.datas.dtos.responses;

import africa.vote.SmartVote.datas.enums.Category;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginData {
    private String token;
    private String imageURL;
    private String firstName;
    private Category category;
}
