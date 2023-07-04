package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "This Field cannot be blank")
    private String firstName;
    @NotBlank(message = "This field cannot be blank")
    private String lastName;
    @Email(message = "This field requires a valid email address")
    @NotBlank(message = "This Field cannot be blank")
    private String email;
    @NotBlank(message = "This field Cannot be Blank")
    private String phoneNumber;
    @NotBlank(message = "This Field cannot be blank")
    private String password;
    @NotBlank(message = "This Field cannot be blank")
    private String category;
    @NotBlank(message = "This Field cannot be blank")
    private String imageURL;
}