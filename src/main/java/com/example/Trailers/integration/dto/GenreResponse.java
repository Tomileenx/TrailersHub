package com.example.Trailers.integration.dto;

import java.util.List;

public record GenreResponse(
        List<TmdbGenre> genres
) {
}
