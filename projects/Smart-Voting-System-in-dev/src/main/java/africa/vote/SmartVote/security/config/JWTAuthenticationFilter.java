package africa.vote.SmartVote.security.config;

import africa.vote.SmartVote.exeptions.GenericException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            try {
                filterChain.doFilter(request, response);
            } catch (IOException | ServletException exception) {
                throw new GenericException(exception.getMessage());
            }
            return;
        }
        token = authHeader.substring(7);
        userEmail = jwtService.extractUserName(token);
        try {
            if(userEmail != null && SecurityContextHolder
                    .getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if(jwtService.isTokenValid(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (SignatureException exception) {
            throw new GenericException(exception.getMessage());
        }
        try {
            filterChain.doFilter(request,response);
        } catch (IOException | ServletException  exception) {
            throw new GenericException(exception.getMessage());
        }
    }
}