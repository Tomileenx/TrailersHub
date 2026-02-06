package com.example.Trailers.config;

import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import com.example.Trailers.roles.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UserAccountRepo userAccountRepo;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userAccountRepo.findUsersByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration configuration) {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CommandLineRunner commandLineRunner(
      UserAccountRepo userAccountRepo,
      PasswordEncoder passwordEncoder) {
    return args -> {
      if (userAccountRepo.findUsersByEmail("admin@example.com").isEmpty()) {
        UserAccount admin = new UserAccount();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setRole(Role.ROLE_ADMIN);

        userAccountRepo.save(admin);

        System.out.println("Admin user created: admin@example.com, Admin@123");
      }
    };
  }
}
