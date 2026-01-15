package com.example.Trailers.integration.dto;

import java.util.List;

public record TrendingResponse(
        List<TmdbMovieSummary> results
) {
}
