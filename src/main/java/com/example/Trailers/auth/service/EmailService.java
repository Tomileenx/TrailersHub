package com.example.Trailers.auth.service;

import com.example.Trailers.config.BrevoProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final WebClient webClient;

    private final BrevoProperties brevoProperties;

    @PostConstruct
    public void init() {
        System.out.println("Brevo API Key: " + brevoProperties.getApiKey());
        System.out.println("Sender Email: " + brevoProperties.getSenderEmail());
        System.out.println("Sender Name: " + brevoProperties.getSenderName());
    }

    public void sendPasswordResetToken(String to, String subject, String body) {
        Objects.requireNonNull(to, "Recipient email cannot be null");
        Objects.requireNonNull(brevoProperties.getSenderEmail(), "Sender email cannot be null");
        Objects.requireNonNull(brevoProperties.getSenderName(), "Sender name cannot be null");


        webClient.post()
                .header("api-key", brevoProperties.getApiKey())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "sender", Map.of(
                                "name", brevoProperties.getSenderName(),
                                "email", brevoProperties.getSenderEmail()
                        ),
                        "to", List.of(Map.of("email", to)),
                        "subject", subject,
                        "textContent", body
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
