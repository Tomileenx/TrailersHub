package com.example.Trailers.trailers.model;

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
  private Long id;

  @Column(nullable = false, unique = true)
  private Long tmdbId;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(length = 1000)
  private String description;

  @Column(nullable = false)
  private String videoUrl;

  @ElementCollection
  @CollectionTable(name = "trailer_genres", joinColumns = @JoinColumn(name = "trailer_id"))
  @Column(name = "genre")
  private Set<String> genres = new HashSet<>();

  private Double rating;

  private LocalDate releaseDate;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToMany(mappedBy = "savedTrailers")
  private Set<UserAccount> usersSavedTrailers = new HashSet<>();
}
