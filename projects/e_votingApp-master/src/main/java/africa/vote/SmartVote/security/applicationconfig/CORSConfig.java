package africa.vote.SmartVote.security.applicationconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig {
        @Bean
        public WebMvcConfigurer corsConfig() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(@NonNull CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOriginPatterns("*")
                            .allowedMethods("*")
                            .allowedHeaders("*")
                            .allowCredentials(true);
                }
            };
        }
}
