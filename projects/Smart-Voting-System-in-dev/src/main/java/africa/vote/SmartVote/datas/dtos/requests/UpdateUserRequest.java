package africa.vote.SmartVote.datas.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String imageURL;
}