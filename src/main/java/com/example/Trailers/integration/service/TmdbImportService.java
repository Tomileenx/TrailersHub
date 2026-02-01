package com.example.Trailers.integration.service;

import com.example.Trailers.integration.cache.GenreCache;
import com.example.Trailers.integration.dto.TmdbMovieSummary;
import com.example.Trailers.integration.dto.TmdbVideo;
import com.example.Trailers.integration.dto.TrendingResponse;
import com.example.Trailers.integration.model.TmdbClient;
import com.example.Trailers.trailers.model.Trailers;
import com.example.Trailers.trailers.repo.TrailersRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TmdbImportService {
  private final TmdbClient tmdbClient;
  private final TrailersRepo trailersRepo;
  private final GenreCache genreCache;

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Transactional
  public void importTrendingMovies() {
    int maxPages = 3;

    for (int page = 1; page <= maxPages; page++) {
      TrendingResponse response = tmdbClient.getTrendingMovies("week", page);

      if (response == null || response.results().isEmpty()) {
        break; // no more results
      }

      for (TmdbMovieSummary movie : response.results()) {
        if (trailersRepo.existsByTmdbId(movie.id())) {
          continue;
        }

        TmdbVideo video = tmdbClient.getTrailer(movie.id());
        if (video == null)
          continue;

        Set<String> genres = movie.genreIds().stream()
            .map(genreCache::getName)
            .collect(Collectors.toSet());

        Trailers trailer = new Trailers();

        trailer.setTmdbId(movie.id());
        trailer.setTitle(movie.title());
        trailer.setDescription(movie.overview());
        trailer.setReleaseDate(movie.releaseDate());
        trailer.setGenres(genres);
        trailer.setRating(movie.voteAverage());
        trailer.setVideoUrl("https://www.youtube.com/watch?v=" + video.key());

        trailersRepo.save(trailer);
      }
    }
  }
}
