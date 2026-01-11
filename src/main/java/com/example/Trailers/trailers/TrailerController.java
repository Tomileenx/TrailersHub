package com.example.Trailers.trailers;

import com.example.Trailers.user.model.UserAccount;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class TrailerController {
    private final TrailerService trailerService;

    @PostMapping("/admin/upload/trailer")
    public ResponseEntity<String> uploadVideo(
            @Valid @RequestBody TrailerRequest request
    ) {
        trailerService.uploadTrailer(request);
        return ResponseEntity.ok(
                "Trailer successfully uploaded"
        );
    }

    public List<TrailerResponse> getTrailerByTitle(
            @RequestParam String title,
            @AuthenticationPrincipal UserAccount userAccount
    ) {
        return trailerService.viewTrailersByTitle(title, userAccount);
    }

    public List<TrailerResponse> getTrailerByGenresIn(
            @RequestParam  Set<String> genres,
            @AuthenticationPrincipal UserAccount userAccount
    ) {
        return trailerService.viewTrailersByGenresIn(genres, userAccount);
    }

    public List<TrailerResponse> getTrailerByFranchise(
            @RequestParam String franchise,
            @AuthenticationPrincipal UserAccount userAccount
    ) {
        return trailerService.viewTrailersByTitle(franchise, userAccount);
    }
}
