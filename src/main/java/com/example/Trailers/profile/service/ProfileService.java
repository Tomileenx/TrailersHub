package com.example.Trailers.profile.service;

import com.example.Trailers.exception.ProfileNotFoundException;
import com.example.Trailers.exception.UserAlreadyExists;
import com.example.Trailers.exception.UserNotFoundException;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import com.example.Trailers.profile.dto.ProfileRequest;
import com.example.Trailers.profile.dto.ProfileResponse;
import com.example.Trailers.profile.dto.ProfileUpdateRequest;
import com.example.Trailers.profile.mapper.ProfileMapper;
import com.example.Trailers.profile.model.Profile;
import com.example.Trailers.profile.repo.ProfileRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;


@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepo profileRepo;
    private final ProfileMapper profileMapper;
    private final UserAccountRepo userAccountRepo;

    @Transactional
    public void createMyProfile(
            ProfileRequest request,
            UserAccount userAccount
    ) {
        if (request == null) {
            throw new IllegalArgumentException("Profile request cannot be null");
        }

        if (userAccount.getUserProfile() != null) {
            throw new UserAlreadyExists("User profile already exists.");
        }

        Profile profile = profileMapper.toProfile(request);

        profile.setUserAccount(userAccount);
        userAccount.setUserProfile(profile);

        userAccountRepo.save(userAccount);
    }

    @Transactional(readOnly = true)
    public ProfileResponse viewMyProfile(UserAccount userAccount) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Profile profile = user.getUserProfile();

        if (profile == null) {
            throw new ProfileNotFoundException("User profile not found");
        }

        return profileMapper.toProfileResponse(profile);
    }

    @Transactional
    public void updateMyProfile(
            ProfileUpdateRequest request,
            UserAccount userAccount
    ) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Profile profile = user.getUserProfile();

        if (profile == null) {
            throw new ProfileNotFoundException("Profile not found");
        }

        if (request.username() != null) {
            profile.setUsername(request.username());
        }

        if (request.favouriteGenres() != null) {
            profile.setFavouriteGenres(new HashSet<>(request.favouriteGenres()));
        }

        if (request.language() != null) {
            profile.setLanguage(
                    profileMapper.mapProfileLanguageToTmdb(request.language())
            );
        }
    }

    @Transactional
    public void deleteMyProfile(UserAccount userAccount) {
        UserAccount user = userAccountRepo.findById(userAccount.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Profile profile = user.getUserProfile();

        if (profile == null) {
            throw new ProfileNotFoundException("Profile not found");
        }

        user.setUserProfile(null);
        profile.setUserAccount(null);

        profileRepo.delete(profile);
    }
}
