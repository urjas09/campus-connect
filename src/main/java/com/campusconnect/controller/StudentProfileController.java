package com.campusconnect.controller;

import com.campusconnect.dto.StudentProfileDto;
import com.campusconnect.service.StudentProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentProfileController {

    private final StudentProfileService profileService;

    public StudentProfileController(StudentProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/profile/{userId}")
    public ResponseEntity<StudentProfileDto> saveOrUpdateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody StudentProfileDto dto) {
        StudentProfileDto saved = profileService.saveOrUpdateProfile(userId, dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<StudentProfileDto> getProfile(@PathVariable Long userId) {
        StudentProfileDto dto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<StudentProfileDto>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }
}
