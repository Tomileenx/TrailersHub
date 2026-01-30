package com.example.Trailers.trailers.repo;

import com.example.Trailers.trailers.model.Trailers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrailersRepo extends JpaRepository<Trailers, Long> {
  Optional<Trailers> findByTmdbId(Long id);

  List<Trailers> findByTitleContainingIgnoreCase(String title);

  @Query("""
            SELECT DISTINCT t FROM Trailers t
            JOIN t.genres g
            WHERE g IN :genres
      """)
  List<Trailers> findByGenresIn(Set<String> genres);

  @Query("""
      SELECT t FROM Trailers t
      WHERE EXTRACT(YEAR FROM t.releaseDate) = :year
      """)
  List<Trailers> findByReleaseYear(@Param("year") int year);

  @Query("""
              SELECT t FROM Trailers t
              WHERE EXTRACT(YEAR FROM t.releaseDate) = :year AND
              EXTRACT(MONTH FROM t.releaseDate) = :month
      """)
  List<Trailers> findByReleaseYearAndMonth(
      @Param("year") int year,
      @Param("month") int month);

  List<Trailers> findByRatingGreaterThanEqual(Double rating);

  boolean existsByTmdbId(Long id);

}
