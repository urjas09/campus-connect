package com.campusconnect.controller;

import com.campusconnect.dto.SkillGapAnalysisDto;
import com.campusconnect.service.SkillGapAnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillGapController {

    private final SkillGapAnalyzerService skillGapService;

    public SkillGapController(SkillGapAnalyzerService skillGapService) {
        this.skillGapService = skillGapService;
    }

    @GetMapping("/analyze")
    public ResponseEntity<SkillGapAnalysisDto> analyze(
            @RequestParam Long studentId,
            @RequestParam Long opportunityId) {
        SkillGapAnalysisDto analysis = skillGapService.analyzeSkillGap(studentId, opportunityId);
        return ResponseEntity.ok(analysis);
    }
}
