package az.phonebook.backend.config;

import az.phonebook.backend.handlers.FeignCustomErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public FeignCustomErrorHandler customErrorHandler() {
        return new FeignCustomErrorHandler();
    }
}
