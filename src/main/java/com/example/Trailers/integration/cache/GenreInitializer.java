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

        GenreResponse response = tmdbClient.getGenres();
        response.genres().forEach(
                g -> genreCache.put(g.id(), g.name())
        );
    }
}
