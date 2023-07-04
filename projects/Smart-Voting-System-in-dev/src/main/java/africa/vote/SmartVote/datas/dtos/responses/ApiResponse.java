package africa.vote.SmartVote.datas.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {
    @JsonFormat(pattern = "yyyy-MM-dd || HH:mm:ss")
    private ZonedDateTime timestamp;
    private HttpStatus status;
    private Object data;
    private String path;
    private boolean isSuccessful;
}