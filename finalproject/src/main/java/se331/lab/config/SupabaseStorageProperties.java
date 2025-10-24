package se331.lab.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "supabase.storage")
public class SupabaseStorageProperties {
    private String endpoint;
    private String endpointOutput;
    private String bucket;
    private String region;
    private String accessKey;
    private String secretKey;
}
