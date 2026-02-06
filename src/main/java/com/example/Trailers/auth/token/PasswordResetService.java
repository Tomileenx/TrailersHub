package com.example.Trailers.auth.token;

import com.example.Trailers.auth.dto.ForgotPasswordRequest;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import com.example.Trailers.auth.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {

    private final UserAccountRepo userAccountRepo;
    private final PasswordTokenRepo passwordTokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public void forgotPassword(ForgotPasswordRequest dto) {
       UserAccount user = userAccountRepo.findUsersByEmail(dto.email())
               .orElseThrow(() -> new UsernameNotFoundException("User not found"));

       passwordTokenRepo.deleteByUser(user);

       String token = UUID.randomUUID().toString();

       PasswordResetToken resetToken = new PasswordResetToken();
       resetToken.setToken(token);
       resetToken.setUser(user);
       resetToken.setExpiresAt(
               Instant.now().plus(30, ChronoUnit.MINUTES)
       );

       passwordTokenRepo.save(resetToken);

       emailService.sendPasswordResetToken(
               user.getEmail(),
               "Password Reset",
               "Reset your password using this token: " + token
       );
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken =
                passwordTokenRepo.findValidToken(token)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Invalid or expired token"));

        UserAccount user = resetToken.getUser();

        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userAccountRepo.save(user);
        passwordTokenRepo.delete(resetToken);
    }
}
