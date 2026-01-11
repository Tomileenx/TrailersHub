package com.example.Trailers.auth.token;

import com.example.Trailers.user.model.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    @Query("""
      select t from PasswordResetToken t
      where t.token = :token
     and t.expiresAt > CURRENT_TIMESTAMP
     """
    )
    Optional<PasswordResetToken> findValidToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetToken t WHERE t.user = :user")
    void deleteByUser(@Param("user") UserAccount userAccount);
}
