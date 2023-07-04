package africa.vote.SmartVote.security.applicationconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime datetime = LocalDateTime.now();
        String formattedDatetime = datetime
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("Status", false);
        data.put("Date Stamp", formattedDatetime);
        data.put("Message", exception.getMessage());
        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}