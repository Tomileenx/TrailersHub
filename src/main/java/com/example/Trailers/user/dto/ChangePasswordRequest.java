package com.example.Trailers.user.dto;

public record ChangePasswordRequest(
    String oldPassword,

    String newPassword) {
}
