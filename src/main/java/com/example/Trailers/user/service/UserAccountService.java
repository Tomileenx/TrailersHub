package com.example.Trailers.user.service;

import com.example.Trailers.exception.UserNotFoundException;
import com.example.Trailers.jwt.JwtService;
import com.example.Trailers.user.mapper.AccountMapper;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserAccountService {
    private final UserAccountRepo userAccountRepo;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Transactional
    public void changePassword(UserAccount userAccount, String oldPassword, String newPassword) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("New password must be different");
        }

        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void deleteMyAccount(UserAccount userAccount) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userAccountRepo.delete(user);
    }
}
