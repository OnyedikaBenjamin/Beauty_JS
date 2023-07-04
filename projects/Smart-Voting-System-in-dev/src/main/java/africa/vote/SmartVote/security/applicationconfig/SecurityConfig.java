package africa.vote.SmartVote.security.applicationconfig;

import africa.vote.SmartVote.security.config.JWTAuthenticationFilter;
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
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAccessDeniedHandler accessDeniedHandler;

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
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jWTAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
        return httpSecurity.build();
    }
}