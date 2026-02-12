package com.example.Trailers.integration.cache;

import com.example.Trailers.integration.dto.GenreResponse;
import com.example.Trailers.integration.model.TmdbClient;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GenreInitializer {
  private final TmdbClient tmdbClient;
  private final GenreCache genreCache;

  @PostConstruct
  public void init() {
    if (!genreCache.isEmpty()) return;

    try {
      GenreResponse response = tmdbClient.getGenres();

      if (response != null && response.genres() != null) {
        response.genres().forEach(
                g -> genreCache.put(g.id(), g.name())
        );
      }

    } catch (Exception e) {
      System.err.println("Genre initialization failed, continuing startup.");
    }
  }
}
