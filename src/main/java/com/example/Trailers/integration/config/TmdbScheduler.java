package com.example.Trailers.integration.config;

import com.example.Trailers.integration.service.TmdbImportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TmdbScheduler {
    private final TmdbImportService tmdbImportService;

    @Scheduled(cron = "0 0 3 * * MON")
    public void syncTrendingMovies() {
        log.info("Running weekly TMDB import");
        tmdbImportService.importTrendingMoviesMax3pages();
    }
}
