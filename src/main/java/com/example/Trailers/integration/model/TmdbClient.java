package com.example.Trailers.integration.model;

import com.example.Trailers.integration.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TmdbClient {
  private final WebClient webClient;
  private static final Logger logger = LoggerFactory.getLogger(TmdbClient.class);

  public TmdbClient(WebClient.Builder builder, @Value("${TMDB_TOKEN:}") String token) {
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
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> {
              logger.error("Error from TMDB API: {}", clientResponse.statusCode());
              return Mono.empty();
            })
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
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> {
              logger.error("Error from TMDB API: {}", clientResponse.statusCode());
              return Mono.empty();
            })
        .bodyToMono(TmdbMovieDetails.class)
        .block();
  }

  public GenreResponse getGenres() {
      try {
          return webClient.get()
                  .uri("/genre/movie/list")
                  .retrieve()
                  .onStatus(HttpStatusCode::isError,
                          ClientResponse::createException)
                  .bodyToMono(GenreResponse.class)
                  .block();
      } catch (Exception e) {
          logger.error("Failed to fetch genres from TMDB", e);
          return null;
      }

  }

  public TmdbVideo getTrailer(Long id) {
    TmdbVideoResponse response = webClient.get()
        .uri("/movie/{id}/videos", id)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> {
              logger.error("Error from TMDB API: {}", clientResponse.statusCode());
              return Mono.empty();
            })
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
