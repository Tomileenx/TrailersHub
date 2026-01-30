package com.example.Trailers.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
    @Email @NotBlank(message = "Email is required") String email,

    @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least eight characters") String password) {
}
