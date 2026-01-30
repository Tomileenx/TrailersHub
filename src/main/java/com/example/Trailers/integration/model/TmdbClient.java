package com.example.Trailers.integration.model;

import com.example.Trailers.integration.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TmdbClient {
  private final WebClient webClient;

  public TmdbClient(WebClient.Builder builder, @Value("${TMDB_TOKEN}") String token) {
    this.webClient = builder
        .baseUrl("https://api.themoviedb.org/3")
        .defaultHeader(HttpHeaders.AUTHORIZATION, token)
        .build();
  }

  public TrendingResponse getTrendingMovies(String timeWindow, int page) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/trending/movie/{timeWindow}")
            .queryParam("page", page)
            .build((timeWindow)))
        .retrieve()
        .bodyToMono(TrendingResponse.class)
        .block();
  }

  public TmdbMovieDetails getMovieDetails(Long id, String language) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/movie/{id}")
            .queryParam("language", language)
            .build((id)))
        .retrieve()
        .bodyToMono(TmdbMovieDetails.class)
        .block();
  }

  public GenreResponse getGenres() {
    return webClient.get()
        .uri("/genre/movie/list")
        .retrieve()
        .bodyToMono(GenreResponse.class)
        .block();
  }

  public TmdbVideo getTrailer(Long id) {
    TmdbVideoResponse response = webClient.get()
        .uri("/movie/{id}/videos", id)
        .retrieve()
        .bodyToMono(TmdbVideoResponse.class)
        .block();

    if (response == null || response.results().isEmpty()) {
      return null;
    }

    return response.results().stream()
        .filter(v -> "Trailer".equals(v.type()))
        .filter(v -> "YouTube".equals(v.site()))
        .findFirst()
        .orElse(null);
  }
}
