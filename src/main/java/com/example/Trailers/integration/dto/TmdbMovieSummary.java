package com.example.Trailers.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record TmdbMovieSummary(
        Long id,
        String title,
        String overview,
        @JsonProperty("genre_ids")
        List<Integer> genreIds,
        @JsonProperty("release_date")
        LocalDate releaseDate,
        @JsonProperty("vote_average")
        Double voteAverage
) {
}
