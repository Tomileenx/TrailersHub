package com.example.Trailers.auth.dto;

public record ResetPasswordRequest(
    String token,
    String newPassword) {
}
