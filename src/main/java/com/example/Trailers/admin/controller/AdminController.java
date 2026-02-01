package com.example.Trailers.admin.controller;

import com.example.Trailers.admin.dto.AdminResponse;
import com.example.Trailers.admin.service.AdminService;
import com.example.Trailers.integration.service.TmdbImportService;
import com.example.Trailers.user.model.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final TmdbImportService tmdbImportService;


    @GetMapping("/admin/admins")
    public List<AdminResponse> getAdminsByRole() {
        return adminService.getAllAdminsByRole();
    }

    @GetMapping("/admin/users")
    public List<AdminResponse> getUsersByRole() {
        return adminService.getAllUsersByRole();
    }

    @PostMapping("/admin/import/trailer")
    public ResponseEntity<String> importTrailer(
            @AuthenticationPrincipal UserAccount userAccount) {
        tmdbImportService.importTrendingMovies();
        return ResponseEntity.ok(
                "Trailer successfully imported");
    }
}
