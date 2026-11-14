package com.campusconnect.controller;

import com.campusconnect.dto.ReadinessScoreDto;
import com.campusconnect.service.ReadinessScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/readiness")
@CrossOrigin(origins = "*")
public class ReadinessScoreController {

    private final ReadinessScoreService readinessService;

    public ReadinessScoreController(ReadinessScoreService readinessService) {
        this.readinessService = readinessService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ReadinessScoreDto> getReadinessScore(@PathVariable Long studentId) {
        ReadinessScoreDto score = readinessService.calculateReadinessScore(studentId);
        return ResponseEntity.ok(score);
    }
}
