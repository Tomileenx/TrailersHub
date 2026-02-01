package com.example.Trailers.trailers.dto;

import java.time.LocalDate;
import java.util.Set;

public record TrailerResponse(
    String title,

    String description,

    LocalDate releaseDate,

    Set<String> genres,

    Double rating,

    String videoUrl) {
}
