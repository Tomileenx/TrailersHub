package com.example.Trailers.profile.controller;

import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.profile.dto.ProfileRequest;
import com.example.Trailers.profile.dto.ProfileResponse;
import com.example.Trailers.profile.service.ProfileService;
import com.example.Trailers.profile.dto.ProfileUpdateRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ProfileController {

  private final ProfileService profileService;

  @PostMapping("/user/create")
  public ResponseEntity<String> createProfile(
      @Valid @RequestBody ProfileRequest request,
      @AuthenticationPrincipal UserAccount userAccount) {
    profileService.createMyProfile(request, userAccount);
    return ResponseEntity.ok(
        "Profile successfully created");
  }

  @GetMapping("/user/profile")
  public ProfileResponse getProfile(
      @AuthenticationPrincipal UserAccount userAccount) {
    return profileService.viewMyProfile(userAccount);
  }

  @PutMapping("/user/update-profile")
  public ResponseEntity<String> updateProfile(
      @Valid @RequestBody ProfileUpdateRequest request,
      @AuthenticationPrincipal UserAccount userAccount) {
    profileService.updateMyProfile(request, userAccount);
    return ResponseEntity.ok(
        "Profile successfully updated");
  }

  @DeleteMapping("/user/delete-profile")
  public ResponseEntity<String> deleteProfile(
      @AuthenticationPrincipal UserAccount userAccount) {
    profileService.deleteMyProfile(userAccount);
    return ResponseEntity.ok(
        "Profile successfully deleted");
  }
}
