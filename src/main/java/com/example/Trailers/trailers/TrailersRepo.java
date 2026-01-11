package com.example.Trailers.trailers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrailersRepo extends JpaRepository<Trailers, Integer> {
        Optional<Trailers> findByTitleIgnoreCase(String title);

        List<Trailers> findByTitleContainingIgnoreCase(String title);

        List<Trailers> findByFranchiseIgnoreCase(String franchise);

        @Query("""
              SELECT DISTINCT t FROM Trailers t
              JOIN t.genres g
              WHERE g IN : genres
        """)
        List<Trailers> findByGenresIn(Set<String> genres);

        @Query("""
                  SELECT DISTINCT t FROM Trailers t
                  JOIN t.genres g
                  WHERE g IN : genres
                  AND t.franchise = :franchise
            """)
        List<Trailers> findByGenresAndFranchise(Set<String> genres, String franchise);
}
