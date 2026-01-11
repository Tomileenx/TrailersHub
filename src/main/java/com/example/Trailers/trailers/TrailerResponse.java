package com.example.Trailers.trailers;

public record TrailerResponse(
        String title,

        String description,

        String videoUrl,

        String duration
) {
}
