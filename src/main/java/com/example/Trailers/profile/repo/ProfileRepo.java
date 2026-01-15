package com.example.Trailers.profile.repo;

import com.example.Trailers.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUserAccountId(Integer id);
    Optional<Profile> findByUsername(String username);
}
