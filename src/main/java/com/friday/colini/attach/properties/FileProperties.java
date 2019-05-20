package com.friday.colini.attach.properties;

import com.friday.colini.attach.type.UploadType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "colini.attach.file")
public class FileProperties {
    private UploadType uploadType;
    private String defaultUploadPath;
}
