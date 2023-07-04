package africa.vote.SmartVote.security.applicationconfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI configAPI(){
        return new OpenAPI().info(new Info()
                .title("Smart Vote API Documentation")
                .version("Version 1.00")
                .description("Documentation for Smart Voting System")
                .contact(new Contact()
                        .name("Lekan Sofuyi")
                        .email("lekan.sofuyi@gmail.com")
                        .url("https://github.com/ola-lekan01/Smart-Voting-System"))
                .termsOfService("An online voting system software platform " +
                        "that allows groups to securely conduct votes and elections."));
    }
}
