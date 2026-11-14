package com.campusconnect.controller;

import com.campusconnect.dto.ApplicationDto;
import com.campusconnect.dto.UpdateStatusRequest;
import com.campusconnect.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApplicationDto> apply(
            @RequestParam Long studentId,
            @RequestParam Long opportunityId) {
        ApplicationDto created = applicationService.apply(studentId, opportunityId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationDto> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request) {
        ApplicationDto updated = applicationService.updateStatus(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationDto>> getStudentApplications(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.getStudentApplications(studentId));
    }

    @GetMapping("/opportunity/{opportunityId}")
    public ResponseEntity<List<ApplicationDto>> getOpportunityApplications(@PathVariable Long opportunityId) {
        return ResponseEntity.ok(applicationService.getOpportunityApplications(opportunityId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }
}
