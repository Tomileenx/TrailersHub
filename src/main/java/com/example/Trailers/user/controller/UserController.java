package com.example.Trailers.user.controller;

import com.example.Trailers.user.dto.ChangePasswordRequest;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.service.UserAccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserController {
    private final UserAccountService userAccountService;

    @PostMapping("user/change-password")
    public ResponseEntity<String> changeMyPassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserAccount userAccount
    ) {
        userAccountService.changePassword(userAccount, request.oldPassword(), request.newPassword());
        return ResponseEntity.ok(
                "Password successfully changed"
        );
    }

    @DeleteMapping("user/delete-account")
    public ResponseEntity<String> deleteMyAccount(
            @AuthenticationPrincipal UserAccount userAccount
    ) {
        userAccountService.deleteMyAccount(userAccount);
        return ResponseEntity.ok(
                "Account successfully deleted"
        );
    }
}
