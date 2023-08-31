package com.example.springbootexplore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data

//需要在启动类添加@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "pinda.file")
public class ConfigurationPropertiesTest {

    @Data
    public static class Properties {
        private String uriPrefix;
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }

    private Properties local;
    private Properties ali;
    private Properties minio;
    private Properties qiniu;
    private Properties tencent;
}
