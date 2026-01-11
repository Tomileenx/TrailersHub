package com.example.Trailers.trailers;

import com.example.Trailers.user.model.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trailers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            unique = true,
            nullable = false
    )
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String videoUrl;

    private String duration;

    @ElementCollection
    @CollectionTable(
            name = "trailer_genres",
            joinColumns = @JoinColumn(name = "trailer_id")
    )
    @Column(name = "genre")
    private Set<String> genres = new HashSet<>();

    @Column(nullable = false)
    private String franchise;

    private LocalDate releaseDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "savedTrailers")
    private Set<UserAccount> usersSavedTrailers = new HashSet<>();
}
