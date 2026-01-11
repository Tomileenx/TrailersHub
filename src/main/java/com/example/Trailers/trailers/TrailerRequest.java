package com.example.Trailers.trailers;

import java.util.Set;

public record TrailerRequest(
        String title,

        String description,

        String videoUrl,

        String duration,

        Set<String> genres,

        String franchise
) {
}
