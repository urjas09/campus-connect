package com.campusconnect.controller;

import com.campusconnect.dto.RecommendationDto;
import com.campusconnect.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
public class RecommendationController {

    private final RecommendationEngineService recommendationService;

    public RecommendationController(RecommendationEngineService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<RecommendationDto>> getRecommendations(@PathVariable Long studentId) {
        List<RecommendationDto> recommendations = recommendationService.getRecommendationsForStudent(studentId);
        return ResponseEntity.ok(recommendations);
    }
}
