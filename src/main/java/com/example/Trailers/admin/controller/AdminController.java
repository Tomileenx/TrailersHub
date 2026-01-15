package com.example.Trailers.admin.controller;

import com.example.Trailers.admin.dto.AdminResponse;
import com.example.Trailers.admin.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;


    @GetMapping("/admin/admins")
    public List<AdminResponse> getAdminsByRole() {
        return adminService.getAllAdminsByRole();
    }

    @GetMapping("/admin/users")
    public List<AdminResponse> getUsersByRole() {
        return adminService.getAllUsersByRole();
    }
}
