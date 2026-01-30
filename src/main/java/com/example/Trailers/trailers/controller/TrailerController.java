package com.example.Trailers.trailers.controller;

import com.example.Trailers.integration.service.TmdbImportService;
import com.example.Trailers.trailers.dto.TrailerResponse;
import com.example.Trailers.trailers.service.TrailerService;
import com.example.Trailers.user.model.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class TrailerController {
  private final TmdbImportService tmdbImportService;
  private final TrailerService trailerService;

  @PostMapping("/admin/import/trailer")
  public ResponseEntity<String> importTrailer(
      @AuthenticationPrincipal UserAccount userAccount) {
    tmdbImportService.importTrendingMoviesMax3pages();
    return ResponseEntity.ok(
        "Trailer successfully imported");
  }

  @GetMapping("/user/trailer/title")
  public List<TrailerResponse> getTrailerByTitle(
      @RequestParam String title,
      @AuthenticationPrincipal UserAccount userAccount) {
    return trailerService.viewTrailersByTitle(title, userAccount);
  }

  @GetMapping("/user/trailer/genres")
  public List<TrailerResponse> getTrailerByGenresIn(
      @RequestParam Set<String> genres,
      @AuthenticationPrincipal UserAccount userAccount) {
    return trailerService.viewTrailersByGenresIn(genres, userAccount);
  }

  @GetMapping("/user/trailer/date")
  public List<TrailerResponse> getTrailerByDate(
      @RequestParam Integer year,
      @RequestParam(required = false) Integer month,
      @AuthenticationPrincipal UserAccount userAccount) {
    return trailerService.viewTrailersByYearOrMonth(year, month, userAccount);
  }

  @GetMapping("/user/trailer/top/rated")
  public List<TrailerResponse> getTrailerByTopRating(
      @AuthenticationPrincipal UserAccount userAccount) {
    return trailerService.viewTrailersByTopRating(userAccount);
  }

  @PostMapping("/user/trailer/save")
  public ResponseEntity<String> saveTrailer(
      @RequestParam String title,
      @AuthenticationPrincipal UserAccount userAccount) {
    trailerService.saveTrailer(title, userAccount);
    return ResponseEntity.ok(
        "Trailer successfully saved");
  }

  @DeleteMapping("/user/trailer/unsave")
  public ResponseEntity<String> unSaveTrailer(
      @RequestParam String title,
      @AuthenticationPrincipal UserAccount userAccount) {
    trailerService.unsaveTrailer(title, userAccount);
    return ResponseEntity.ok(
        "Trailer successfully unsaved");
  }

  @GetMapping("/user/trailer/saved")
  public List<TrailerResponse> getSavedTrailers(
      @AuthenticationPrincipal UserAccount userAccount) {
    return trailerService.getSavedTrailers(userAccount);
  }
}
