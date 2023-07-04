package africa.vote.SmartVote.exeptions;

import africa.vote.SmartVote.utils.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(GenericException genericException,
                                                    HttpServletRequest httpServletRequest){
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .isSuccessful(false)
                .data(genericException.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtException(JwtException exception,
                                                       HttpServletRequest httpServletRequest){
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .isSuccessful(false)
                .data(exception.getMessage())
                .statusCode(HttpStatus.FORBIDDEN)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}