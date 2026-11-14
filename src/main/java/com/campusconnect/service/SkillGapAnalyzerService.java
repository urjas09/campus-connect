package com.campusconnect.service;

import com.campusconnect.dto.SkillGapAnalysisDto;
import com.campusconnect.dto.SkillStatus;
import com.campusconnect.entity.Opportunity;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.OpportunityRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillGapAnalyzerService {

    private final StudentProfileRepository profileRepository;
    private final OpportunityRepository opportunityRepository;

    // Related skill map to detect "NEEDS_IMPROVEMENT" (foundational skill exists, advanced missing)
    private static final Map<String, List<String>> RELATED_SKILLS = new HashMap<>();

    static {
        RELATED_SKILLS.put("spring boot", List.of("java", "spring", "backend"));
        RELATED_SKILLS.put("microservices", List.of("spring boot", "java", "docker"));
        RELATED_SKILLS.put("react", List.of("javascript", "html", "css", "web development"));
        RELATED_SKILLS.put("angular", List.of("typescript", "javascript", "html"));
        RELATED_SKILLS.put("docker", List.of("linux", "git", "devops"));
        RELATED_SKILLS.put("kubernetes", List.of("docker", "cloud", "devops"));
        RELATED_SKILLS.put("aws", List.of("cloud", "linux", "networking"));
        RELATED_SKILLS.put("mysql", List.of("sql", "database", "rdbms"));
        RELATED_SKILLS.put("postgresql", List.of("sql", "mysql", "database"));
        RELATED_SKILLS.put("mongodb", List.of("nosql", "database", "json"));
        RELATED_SKILLS.put("machine learning", List.of("python", "data science", "statistics"));
        RELATED_SKILLS.put("pytorch", List.of("python", "machine learning", "deep learning"));
    }

    public SkillGapAnalyzerService(StudentProfileRepository profileRepository,
                                   OpportunityRepository opportunityRepository) {
        this.profileRepository = profileRepository;
        this.opportunityRepository = opportunityRepository;
    }

    public SkillGapAnalysisDto analyzeSkillGap(Long studentId, Long opportunityId) {
        StudentProfile student = profileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found with id: " + studentId));

        Opportunity opp = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found with id: " + opportunityId));

        Set<String> studentSkills = parseSkills(student.getSkills());

        List<String> requiredSkills = Arrays.stream(opp.getRequiredSkills().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        List<SkillGapAnalysisDto.SkillItemDto> breakdown = new ArrayList<>();
        int available = 0;
        int needsImprovement = 0;
        int missing = 0;

        for (String req : requiredSkills) {
            String normalized = req.toLowerCase();

            if (studentSkills.contains(normalized)) {
                available++;
                breakdown.add(new SkillGapAnalysisDto.SkillItemDto(
                        req,
                        SkillStatus.AVAILABLE,
                        "Matched directly with student profile skills",
                        "Keep practicing hands-on problem solving in " + req
                ));
            } else if (hasRelatedSkill(normalized, studentSkills)) {
                needsImprovement++;
                String foundation = getRelatedSkillFound(normalized, studentSkills);
                breakdown.add(new SkillGapAnalysisDto.SkillItemDto(
                        req,
                        SkillStatus.NEEDS_IMPROVEMENT,
                        "Student has foundational knowledge (" + foundation + ") but lacks direct " + req + " proficiency",
                        "Build a mini project combining " + foundation + " with " + req + " to bridge this gap"
                ));
            } else {
                missing++;
                breakdown.add(new SkillGapAnalysisDto.SkillItemDto(
                        req,
                        SkillStatus.MISSING,
                        "No direct or related skill found in student profile",
                        "Start with a beginner tutorial or crash course on " + req
                ));
            }
        }

        // Skill Coverage calculation (AVAILABLE = 100%, NEEDS_IMPROVEMENT = 50%, MISSING = 0%)
        double coverage = 0.0;
        if (!requiredSkills.isEmpty()) {
            double weightedScore = (available * 1.0) + (needsImprovement * 0.5);
            coverage = Math.round((weightedScore / requiredSkills.size()) * 1000.0) / 10.0;
        }

        SkillGapAnalysisDto dto = new SkillGapAnalysisDto();
        dto.setStudentId(student.getId());
        dto.setOpportunityId(opp.getId());
        dto.setOpportunityTitle(opp.getTitle());
        dto.setCompanyName(opp.getCompanyName());
        dto.setTotalRequiredSkills(requiredSkills.size());
        dto.setAvailableCount(available);
        dto.setNeedsImprovementCount(needsImprovement);
        dto.setMissingCount(missing);
        dto.setSkillCoveragePercentage(coverage);
        dto.setSkillsBreakdown(breakdown);

        return dto;
    }

    private boolean hasRelatedSkill(String requiredSkill, Set<String> studentSkills) {
        List<String> prereqs = RELATED_SKILLS.get(requiredSkill);
        if (prereqs == null) return false;
        for (String p : prereqs) {
            if (studentSkills.contains(p.toLowerCase())) return true;
        }
        return false;
    }

    private String getRelatedSkillFound(String requiredSkill, Set<String> studentSkills) {
        List<String> prereqs = RELATED_SKILLS.get(requiredSkill);
        if (prereqs == null) return "basics";
        for (String p : prereqs) {
            if (studentSkills.contains(p.toLowerCase())) return p;
        }
        return "basics";
    }

    private Set<String> parseSkills(String skills) {
        if (skills == null || skills.trim().isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(skills.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
