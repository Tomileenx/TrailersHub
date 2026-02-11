package com.example.Trailers.trailers.service;

import com.example.Trailers.exception.TrailerNotFoundException;
import com.example.Trailers.exception.UserNotFoundException;
import com.example.Trailers.profile.mapper.ProfileMapper;
import com.example.Trailers.trailers.mapper.TrailerMapper;
import com.example.Trailers.trailers.model.Trailers;
import com.example.Trailers.trailers.dto.TrailerResponse;
import com.example.Trailers.trailers.repo.TrailersRepo;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TrailerService {
    private final TrailersRepo trailersRepo;
    private final UserAccountRepo userAccountRepo;
    private final TrailerMapper trailerMapper;
    private final ProfileMapper profileMapper;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Transactional(readOnly = true)
    public List<TrailerResponse> viewTrailersByTitle(String title, UserAccount userAccount) {
       List<Trailers> trailers = trailersRepo.findByTitleIgnoreCase(title);

       if (trailers.isEmpty()) {
           throw new TrailerNotFoundException("No trailer found by that title");
       }

       String language = userAccount.getUserProfile().getLanguage();

       return trailers
               .stream()
               .map(t -> trailerMapper.toTrailerResponse(t, language))
               .toList();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Transactional(readOnly = true)
    public List<TrailerResponse> viewTrailersByGenresIn(
            Set<String> genres,
            UserAccount userAccount
    ) {
        List<Trailers> trailers = trailersRepo.findByGenresIn(genres);

        if (trailers.isEmpty()) {
            throw new TrailerNotFoundException("No trailers found by that genre");
        }

        String language = userAccount.getUserProfile().getLanguage();

        return trailers
                .stream()
                .map(t -> trailerMapper.toTrailerResponse(t, language))
                .toList();
    }

    public List<TrailerResponse> viewTrailersByYearOrMonth(
            Integer year,
            Integer month,
            UserAccount userAccount
    ) {
        List<Trailers> trailers = List.of();

        if (month == null) {
            trailers = trailersRepo.findByReleaseYear(year);
        } else {
            trailers = trailersRepo.findByReleaseYearAndMonth(year, month);
        }

        if (trailers.isEmpty()) {
            throw new TrailerNotFoundException("No trailers found by the given date");
        }

        String language = userAccount.getUserProfile().getLanguage();

        return trailers
                .stream()
                .map(t -> trailerMapper.toTrailerResponse(t, language))
                .toList();
    }

    public List<TrailerResponse> viewTrailersByTopRating (
            UserAccount userAccount
    ) {
        List<Trailers> trailers = trailersRepo.findByRatingGreaterThanEqual(7.0);

        if (trailers.isEmpty()) {
            throw new TrailerNotFoundException("No trailers found by the specified rating");
        }

        String language = userAccount.getUserProfile().getLanguage();

        return trailers
                .stream()
                .map(t -> trailerMapper.toTrailerResponse(t, language))
                .toList();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Transactional
    public void saveTrailer(String title, UserAccount userAccount) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Trailers trailer = trailersRepo.findByTitleContainingIgnoreCase(title)
                .orElseThrow(() -> new TrailerNotFoundException("Trailer not found"));

        boolean alreadySaved = user.getSavedTrailers().stream()
                        .anyMatch(t -> t.getId().equals(trailer.getId()));

        if (alreadySaved) {
            throw new IllegalArgumentException("Trailer is already saved");
        }

        user.getSavedTrailers().add(trailer);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Transactional
    public void unsaveTrailer(String title, UserAccount userAccount) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        Trailers trailer = trailersRepo.findByTitleContainingIgnoreCase(title)
                .orElseThrow(() -> new TrailerNotFoundException("Trailer not found"));

        boolean removed = user.getSavedTrailers()
                .removeIf(t -> t.getId().equals(trailer.getId()));

        if (!removed) {
            throw new IllegalArgumentException("Trailer is not saved");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Transactional(readOnly = true)
    public List<TrailerResponse> getSavedTrailers(UserAccount userAccount) {

        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        String language = user.getUserProfile().getLanguage();

        return user.getSavedTrailers().stream()
                .map(t -> trailerMapper.toTrailerResponse(t, language))
                .toList();

    }
}


