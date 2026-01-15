package com.example.Trailers.integration.dto;

import java.util.List;

public record TmdbVideoResponse(
        List<TmdbVideo> results
) {
}
