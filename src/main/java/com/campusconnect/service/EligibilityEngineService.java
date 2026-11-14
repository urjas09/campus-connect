package com.campusconnect.service;

import com.campusconnect.dto.EligibilityResultDto;
import com.campusconnect.entity.Opportunity;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.OpportunityRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EligibilityEngineService {

    private final StudentProfileRepository profileRepository;
    private final OpportunityRepository opportunityRepository;

    public EligibilityEngineService(StudentProfileRepository profileRepository,
                                   OpportunityRepository opportunityRepository) {
        this.profileRepository = profileRepository;
        this.opportunityRepository = opportunityRepository;
    }

    public EligibilityResultDto evaluateEligibility(Long studentId, Long opportunityId) {
        StudentProfile student = profileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found with id: " + studentId));

        Opportunity opp = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found with id: " + opportunityId));

        List<String> passed = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        // 1. Deadline Check
        if (opp.getApplicationDeadline().isBefore(LocalDate.now())) {
            missing.add("Application deadline passed on " + opp.getApplicationDeadline());
        } else {
            passed.add("Application is currently open (Deadline: " + opp.getApplicationDeadline() + ")");
        }

        // 2. CGPA Check
        if (student.getCgpa() >= opp.getMinCgpa()) {
            passed.add(String.format("CGPA criteria met: %.2f >= %.2f", student.getCgpa(), opp.getMinCgpa()));
        } else {
            missing.add(String.format("CGPA below required threshold: %.2f < %.2f", student.getCgpa(), opp.getMinCgpa()));
        }

        // 3. Branch Check
        Set<String> allowedBranches = Arrays.stream(opp.getAllowedBranches().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        if (allowedBranches.contains(student.getBranch().toUpperCase())) {
            passed.add("Branch eligible: " + student.getBranch() + " is permitted");
        } else {
            missing.add("Branch not eligible: " + student.getBranch() + " (Allowed: " + opp.getAllowedBranches() + ")");
        }

        // 4. Graduation Year Check
        if (student.getGraduationYear().equals(opp.getGraduationYear())) {
            passed.add("Graduation batch matched: " + student.getGraduationYear());
        } else {
            missing.add(String.format("Graduation batch mismatch: %d (Drive is for %d)",
                    student.getGraduationYear(), opp.getGraduationYear()));
        }

        // 5. Active Backlogs Check
        if (student.getActiveBacklogs() <= opp.getMaxBacklogs()) {
            passed.add(String.format("Backlogs criteria met: %d active <= %d allowed",
                    student.getActiveBacklogs(), opp.getMaxBacklogs()));
        } else {
            missing.add(String.format("Too many active backlogs: %d > %d allowed",
                    student.getActiveBacklogs(), opp.getMaxBacklogs()));
        }

        // 6. Skills Coverage Check
        Set<String> studentSkills = parseSkillsToSet(student.getSkills());
        List<String> oppRequiredSkills = Arrays.stream(opp.getRequiredSkills().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        int matchedSkillsCount = 0;
        for (String reqSkill : oppRequiredSkills) {
            if (studentSkills.contains(reqSkill.toLowerCase())) {
                matchedSkillsCount++;
            } else {
                missingSkills.add(reqSkill);
            }
        }

        if (missingSkills.isEmpty()) {
            passed.add("All required skills present (" + opp.getRequiredSkills() + ")");
        } else {
            passed.add("Possesses " + matchedSkillsCount + " of " + oppRequiredSkills.size() + " required skills");
        }

        // Overall Eligibility: Must pass all academic and batch constraints
        boolean isEligible = missing.isEmpty();

        // Calculate transparent Match Percentage:
        // Academic criteria weight = 60%, Skills coverage weight = 40%
        int totalHardCriteria = 4; // CGPA, Branch, Year, Backlogs
        int passedHardCriteria = 4 - (int) missing.stream()
                .filter(m -> !m.contains("deadline passed"))
                .count();
        if (passedHardCriteria < 0) passedHardCriteria = 0;

        double academicScore = ((double) passedHardCriteria / totalHardCriteria) * 60.0;
        double skillScore = oppRequiredSkills.isEmpty() ? 40.0 :
                ((double) matchedSkillsCount / oppRequiredSkills.size()) * 40.0;
        double matchPercentage = Math.round((academicScore + skillScore) * 10.0) / 10.0;

        return new EligibilityResultDto(
                opp.getId(),
                opp.getCompanyName(),
                opp.getTitle(),
                isEligible,
                matchPercentage,
                passed,
                missing,
                missingSkills
        );
    }

    private Set<String> parseSkillsToSet(String skills) {
        if (skills == null || skills.trim().isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(skills.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}
