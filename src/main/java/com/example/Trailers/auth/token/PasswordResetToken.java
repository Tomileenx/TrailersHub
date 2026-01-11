package com.example.Trailers.auth.token;

import com.example.Trailers.user.model.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Column(nullable = false)
    private Instant expiresAt;
}
