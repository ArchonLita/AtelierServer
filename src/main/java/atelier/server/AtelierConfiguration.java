package atelier.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@ConfigurationProperties(prefix = "atelier")
@Configuration
@Data
public class AtelierConfiguration {
    private String databasePath;

    @Bean(name = { "objectMapper" })
    ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }
}
