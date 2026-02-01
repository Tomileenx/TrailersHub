package com.example.Trailers.profile.dto;

import java.util.Set;

public record ProfileResponse(
    String username,

    String UserEmail,

    Set<String> favouriteGenres,

    String language

) {
}
