package com.example.Trailers.trailers;

import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TrailerService {
    private final TrailersRepo trailersRepo;
    private final UserAccountRepo userAccountRepo;
    private final TrailerMapper trailerMapper;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void uploadTrailer(TrailerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (trailersRepo.findByTitleIgnoreCase(request.title()).isPresent()) {
            throw new EntityExistsException("Trailer already exists");
        }

        Trailers trailers = trailerMapper.toTrailer(request);

        trailersRepo.save(trailers);
    }

    @Transactional(readOnly = true)
    public List<TrailerResponse> viewTrailersByTitle(String title, UserAccount userAccount) {
       List<Trailers> trailers = trailersRepo.findByTitleContainingIgnoreCase(title);

       if (trailers.isEmpty()) {
           throw new EntityNotFoundException("No trailer found by that title");
       }

       return trailers.stream()
               .map(trailerMapper::toTrailerResponse)
               .toList();
    }

    @Transactional(readOnly = true)
    public List<TrailerResponse> viewTrailersByGenresIn(Set<String> genres, UserAccount userAccount) {
        List<Trailers> trailers = trailersRepo.findByGenresIn(genres);

        if (trailers.isEmpty()) {
            throw new EntityNotFoundException("No trailers found by that genre");
        }

        return trailers.stream()
                .map(trailerMapper::toTrailerResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrailerResponse> viewTrailersByFranchise(String franchise, UserAccount userAccount) {
        List<Trailers> trailers = trailersRepo.findByFranchiseIgnoreCase(franchise);

        if (trailers.isEmpty()) {
            throw new EntityNotFoundException("No trailers found by that franchise");
        }

        return trailers.stream()
                .map(trailerMapper::toTrailerResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrailerResponse> viewTrailersByGenresAndFranchise(
            Set<String> genres,
            String franchise,
            UserAccount userAccount
    ) {
        List<Trailers> trailers = trailersRepo.findByGenresAndFranchise(genres, franchise);

        if (trailers.isEmpty()) {
            throw new EntityNotFoundException("No trailers found by that genres and franchise");
        }

        return trailers.stream()
                .map(trailerMapper::toTrailerResponse)
                .toList();
    }

    @Transactional
    public void saveTrailer(String title, UserAccount userAccount) {
        Trailers trailer = trailersRepo.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new EntityNotFoundException("Trailer not found"));

        if (userAccount.getSavedTrailers().contains(trailer)) {
            throw new IllegalStateException("Trailer is already saved");
        }

        userAccount.getSavedTrailers().add(trailer);

        userAccountRepo.save(userAccount);
    }

    public void unsaveTrailer(String title, UserAccount userAccount) {
        Trailers trailer = trailersRepo.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new EntityNotFoundException("Trailer not found"));

        if (!userAccount.getSavedTrailers().contains(trailer)) {
            throw new IllegalStateException("Trailer is not saved");
        }

        userAccount.getSavedTrailers().remove(trailer);

        userAccountRepo.save(userAccount);
    }

    public List<TrailerResponse> getSavedTrailers(UserAccount userAccount) {
        return userAccount.getSavedTrailers().stream()
                .map(trailerMapper::toTrailerResponse)
                .toList();
    }
}


