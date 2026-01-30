package com.example.Trailers.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMovieDetails(
    Long id,
    @JsonProperty("belongs_to_collection") TmdbCollection collection,
    String title,
    String overview,
    @JsonProperty("original_language") String originalLanguage) {
}
