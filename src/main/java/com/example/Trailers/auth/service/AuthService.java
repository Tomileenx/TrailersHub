package com.example.Trailers.auth.service;

import com.example.Trailers.user.mapper.AccountMapper;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import com.example.Trailers.auth.dto.AuthRequest;
import com.example.Trailers.auth.dto.AuthResponse;
import com.example.Trailers.exception.UserAlreadyExists;
import com.example.Trailers.jwt.JwtService;
import com.example.Trailers.roles.Role;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserAccountRepo userAccountRepo;
  private final AccountMapper accountMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(AuthRequest dto) {
    userAccountRepo.findUsersByEmail(dto.email())
        .ifPresent(userAccount -> {
          throw new UserAlreadyExists(
              "user with " + dto.email() + " already exists");
        });

    UserAccount userAccount = accountMapper.toUserAccount(dto);

    userAccount.setPassword(passwordEncoder.encode(dto.password()));
    userAccount.setRole(Role.ROLE_USER);

    userAccountRepo.save(userAccount);

    String jwtToken = jwtService.generateToken(userAccount);

    return new AuthResponse(
        jwtToken,
        "Registration successful");
  }

  public AuthResponse login(AuthRequest dto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            dto.email(),
            dto.password()));

    var user = userAccountRepo.findUsersByEmail(dto.email())
        .orElseThrow(() -> new UsernameNotFoundException(dto.email()));

    String jwtToken = jwtService.generateToken(user);

    return new AuthResponse(
        jwtToken,
        "Login is successful");
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public AuthResponse registerAdmin(AuthRequest dto) {
    userAccountRepo.findUsersByEmail(dto.email())
        .ifPresent(userAccount -> {
          throw new UserAlreadyExists("admin with " + dto.email() + "already exists");
        });

    UserAccount userAccount = accountMapper.toUserAccount(dto);

    userAccount.setEmail(dto.email());
    userAccount.setRole(Role.ROLE_ADMIN);

    userAccountRepo.save(userAccount);

    String jwtToken = jwtService.generateToken(userAccount);

    return new AuthResponse(
        jwtToken,
        "Admin registered successfully");
  }
}
