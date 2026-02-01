package com.example.Trailers.auth.controller;

import com.example.Trailers.auth.dto.AuthRequest;
import com.example.Trailers.auth.dto.AuthResponse;
import com.example.Trailers.auth.dto.ForgotPasswordRequest;
import com.example.Trailers.auth.dto.ResetPasswordRequest;
import com.example.Trailers.auth.service.AuthService;
import com.example.Trailers.auth.token.PasswordResetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;
  private PasswordResetService passwordResetService;

  @PostMapping("/account/register")
  public AuthResponse register(
      @Valid @RequestBody AuthRequest request) {
    return authService.register(request);
  }

  @PostMapping("/account/login")
  public AuthResponse login(
      @Valid @RequestBody AuthRequest request) {
    return authService.login(request);
  }

  @PostMapping("/account/forgot-password")
  public ResponseEntity<String> forgotPassword(
      @RequestBody ForgotPasswordRequest request) {
    passwordResetService.forgotPassword(request);
    return ResponseEntity.ok(
        "A token has been sent to your mail, to reset your password");
  }

  @PostMapping("/account/reset-password")
  public ResponseEntity<String> resetPassword(
      @RequestBody ResetPasswordRequest request) {
    passwordResetService.resetPassword(request.token(), request.newPassword());
    return ResponseEntity.ok(
        "Password successfully reset");
  }

  @PostMapping("/admin/account/register")
  public AuthResponse registerAdmin(
      @Valid @RequestBody AuthRequest dto) {
    return authService.registerAdmin(dto);
  }
}
