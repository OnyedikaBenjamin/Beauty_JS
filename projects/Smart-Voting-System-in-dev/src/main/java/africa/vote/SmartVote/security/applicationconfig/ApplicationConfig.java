package africa.vote.SmartVote.security.applicationconfig;

import africa.vote.SmartVote.datas.repositories.UserRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository
                .findByEmailIgnoreCase(username)
                .orElseThrow(() ->
                        new GenericException("User Not found"));
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authConfig) {
        try {
            return authConfig.getAuthenticationManager();
        }
        catch (Exception exception) {
            throw new GenericException(exception.getMessage());
        }
    }
    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();}
    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}
}