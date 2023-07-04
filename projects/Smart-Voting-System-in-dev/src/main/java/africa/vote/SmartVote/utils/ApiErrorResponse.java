package africa.vote.SmartVote.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Builder
@Data
public class ApiErrorResponse {
    private ZonedDateTime timeStamp;
    private HttpStatus statusCode;
    private String path;
    private Object data;
    private Boolean isSuccessful;
}
