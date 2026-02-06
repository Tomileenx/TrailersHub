package com.example.Trailers.profile.mapper;

import com.example.Trailers.profile.dto.ProfileRequest;
import com.example.Trailers.profile.dto.ProfileResponse;
import com.example.Trailers.profile.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
  public String mapProfileLanguageToTmdb(String profileLang) {
    return switch (profileLang.toLowerCase()) {
      case "english", "en" -> "en-US";
      case "french", "fr" -> "fr-FR";
      case "spanish", "es" -> "es-ES";
      default -> "en-US"; // fallback
    };
  }

  public Profile toProfile(ProfileRequest request) {
    Profile profile = new Profile();
    profile.setUsername(request.username());
    profile.setFavouriteGenres(request.favouriteGenres());
    profile.setLanguage((mapProfileLanguageToTmdb(request.language())));

    return profile;
  }

  public ProfileResponse toProfileResponse(Profile profile) {
    if (profile == null) {
      throw new IllegalStateException("Profile is null");
    }

    String email = profile.getUserAccount() != null
        ? profile.getUserAccount().getEmail()
        : null;

    return new ProfileResponse(
        profile.getUsername(),
        email,
        profile.getFavouriteGenres(),
        profile.getLanguage());
  }
}
