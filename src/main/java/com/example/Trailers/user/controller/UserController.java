package com.example.Trailers.user.controller;

import com.example.Trailers.user.dto.ChangePasswordRequest;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
