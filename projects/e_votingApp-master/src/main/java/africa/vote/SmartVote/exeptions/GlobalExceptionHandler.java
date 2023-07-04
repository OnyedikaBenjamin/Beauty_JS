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

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException exception,
                                               HttpServletRequest httpServletRequest){
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .isSuccessful(false)
                .data(exception.getMessage())
                .statusCode(HttpStatus.BAD_GATEWAY)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> userNameNotFoundException(UsernameNotFoundException exception,
                                               HttpServletRequest httpServletRequest){
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .isSuccessful(false)
                .data(exception.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<?> servletExceptionException(ServletException exception,
                                                       HttpServletRequest httpServletRequest){
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .isSuccessful(false)
                .data(exception.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception,
                                          HttpServletRequest httpServletRequest){
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .isSuccessful(false)
                .data(exception.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST)
                .path(httpServletRequest.getRequestURI())
                .timeStamp(ZonedDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}