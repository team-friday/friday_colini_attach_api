package com.friday.colini.attach.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "colini.attach.aws")
public class AwsProperties {
    private String accessKey;
    private String secretKey;
    private String region;

    private S3Properties s3;
}
