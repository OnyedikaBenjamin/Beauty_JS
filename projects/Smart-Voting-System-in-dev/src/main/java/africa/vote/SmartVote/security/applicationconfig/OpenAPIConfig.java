package africa.vote.SmartVote.security.applicationconfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition
@Configuration
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenAPIConfig {

    Contact lekan = new Contact()
                        .name("Lekan Sofuyi")
                        .email("lekan.sofuyi@gmail.com")
                        .url("https://github.com/ola-lekan01/Smart-Voting-System");


    Contact yusuf = new Contact()
            .name("Yusuf Kabir")
            .email("@gmail.com")
            .url("https://github.com/ola-lekan01/Smart-Voting-System");


    Contact james = new Contact()
            .name("James Aduloju")
            .email("adulojujames457@gmail.com")
            .url("https://github.com/ola-lekan01/Smart-Voting-System");
    @Bean
    public OpenAPI configAPI(){
        return new OpenAPI().info(new Info()
                .title("Smart Vote API Documentation")
                .version("Version 1.00")
                .description("Documentation for Smart Voting System")
                .contact(james)
                .contact(yusuf)
                .contact(lekan)
                .termsOfService("An online voting system software platform " +
                        "that allows groups to securely conduct votes and elections."));
    }
}