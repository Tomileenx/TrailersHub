package com.example.Trailers.user.repo;

import com.example.Trailers.roles.Role;
import com.example.Trailers.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepo extends JpaRepository<UserAccount, Integer> {
  Optional<UserAccount> findUsersByEmail(String email);

  List<UserAccount> findUsersByRole(Role role);
}
