package atelier.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "atelier")
@Component
@Data
public class AtelierConfiguration {
    private String databasePath;
}
