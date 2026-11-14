package com.campusconnect.controller;

import com.campusconnect.dto.PlacementAnalyticsDto;
import com.campusconnect.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/overview")
    public ResponseEntity<PlacementAnalyticsDto> getOverview() {
        return ResponseEntity.ok(analyticsService.getPlacementAnalytics());
    }
}
