package com.example.Trailers.trailers;

import org.springframework.stereotype.Component;

@Component
public class TrailerMapper {
    public Trailers toTrailer(TrailerRequest request) {
        Trailers trailers = new Trailers();

        trailers.setTitle(request.title());
        trailers.setDescription(request.description());
        trailers.setVideoUrl(request.videoUrl());
        trailers.setDuration(request.duration());
        trailers.setGenres(request.genres());
        trailers.setFranchise(request.franchise());

        return trailers;
    }

    public TrailerResponse toTrailerResponse(Trailers trailers) {
        return new TrailerResponse(
                trailers.getTitle(),
                trailers.getDescription(),
                trailers.getVideoUrl(),
                trailers.getDuration()
        );
    }
}
