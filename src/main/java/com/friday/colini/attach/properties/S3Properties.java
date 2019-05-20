package com.friday.colini.attach.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class S3Properties {
    private boolean disable;
    private String bucketName;
}
