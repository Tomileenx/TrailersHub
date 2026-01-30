package com.example.Trailers.admin.service;

import com.example.Trailers.admin.dto.AdminResponse;
import com.example.Trailers.roles.Role;
import com.example.Trailers.user.repo.UserAccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {
  private final UserAccountRepo userAccountRepo;

  public List<AdminResponse> getAllAdminsByRole() {
    return userAccountRepo.findUsersByRole(Role.ROLE_ADMIN)
        .stream()
        .map(userAccount -> new AdminResponse(
            userAccount.getId(),
            userAccount.getEmail(),
            userAccount.getRole().name()))
        .collect(Collectors.toList());
  }

  public List<AdminResponse> getAllUsersByRole() {
    return userAccountRepo.findUsersByRole(Role.ROLE_USER)
        .stream()
        .map(userAccount -> new AdminResponse(
            userAccount.getId(),
            userAccount.getEmail(),
            userAccount.getRole().name()))
        .collect(Collectors.toList());
  }
}
