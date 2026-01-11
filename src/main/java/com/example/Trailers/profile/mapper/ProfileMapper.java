package com.example.Trailers.profile.mapper;

import com.example.Trailers.profile.dto.ProfileRequest;
import com.example.Trailers.profile.dto.ProfileResponse;
import com.example.Trailers.profile.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile toProfile(ProfileRequest request) {
        Profile profile = new Profile();
        profile.setUsername(request.username());
        profile.setFavouriteGenres(request.favouriteGenres());
        profile.setFavouriteFranchises(request.favouriteFranchises());

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
                profile.getFavouriteFranchises()
        );
    }
}
