package com.example.Trailers.trailers.mapper;

import com.example.Trailers.integration.model.TmdbClient;
import com.example.Trailers.integration.dto.TmdbMovieDetails;
import com.example.Trailers.trailers.dto.TrailerResponse;
import com.example.Trailers.trailers.model.Trailers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrailerMapper {

    private final TmdbClient tmdbClient;

    public TrailerResponse toTrailerResponse(
            Trailers trailers,
            String language
    ) {
        TmdbMovieDetails details = tmdbClient.getMovieDetails(trailers.getTmdbId(), language);

        return new TrailerResponse(
                details.title(),
                details.overview(),
                trailers.getReleaseDate(),
                trailers.getGenres(),
                trailers.getRating(),
                trailers.getVideoUrl()
        );
    }
}
