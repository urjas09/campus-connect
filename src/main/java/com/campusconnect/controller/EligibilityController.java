package com.campusconnect.controller;

import com.campusconnect.dto.EligibilityResultDto;
import com.campusconnect.service.EligibilityEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
@CrossOrigin(origins = "*")
public class EligibilityController {

    private final EligibilityEngineService eligibilityService;

    public EligibilityController(EligibilityEngineService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    @GetMapping("/check")
    public ResponseEntity<EligibilityResultDto> checkEligibility(
            @RequestParam Long studentId,
            @RequestParam Long opportunityId) {
        EligibilityResultDto result = eligibilityService.evaluateEligibility(studentId, opportunityId);
        return ResponseEntity.ok(result);
    }
}
