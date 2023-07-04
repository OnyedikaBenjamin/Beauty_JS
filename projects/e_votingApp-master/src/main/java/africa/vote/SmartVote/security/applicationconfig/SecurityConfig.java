package africa.vote.SmartVote.security.applicationconfig;

import africa.vote.SmartVote.security.config.JWTAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthenticationFilter jWTAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/api/v1/registration/**",
            "/api/v1/user/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jWTAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((req, res, exception) ->
                        res.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED, exception.getMessage()))
                .accessDeniedHandler((req, res, exception)
                        -> res.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getMessage()));
        return httpSecurity.build();
    }
}