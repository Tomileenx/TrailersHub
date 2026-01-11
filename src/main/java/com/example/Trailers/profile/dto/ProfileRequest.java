package com.example.Trailers.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ProfileRequest(
        @NotBlank
        String username,

        @NotNull
        @Size(max = 3, message = "Maximum of three genres allowed")
        Set<String> favouriteGenres,

        @NotNull
        @Size(max = 3, message = "Maximum of three franchises allowed")
        Set<String> favouriteFranchises
) {
}
