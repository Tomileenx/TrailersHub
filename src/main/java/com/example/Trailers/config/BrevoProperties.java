package com.example.Trailers.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "brevo")
@Getter
@Setter
public class BrevoProperties {
    private String apiKey;

    private String senderEmail;

    private String senderName;
}
