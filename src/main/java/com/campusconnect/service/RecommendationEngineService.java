package com.campusconnect.service;

import com.campusconnect.dto.RecommendationDto;
import com.campusconnect.entity.Opportunity;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.ApplicationRepository;
import com.campusconnect.repository.OpportunityRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationEngineService {

    private final StudentProfileRepository profileRepository;
    private final OpportunityRepository opportunityRepository;
    private final ApplicationRepository applicationRepository;

    public RecommendationEngineService(StudentProfileRepository profileRepository,
                                       OpportunityRepository opportunityRepository,
                                       ApplicationRepository applicationRepository) {
        this.profileRepository = profileRepository;
        this.opportunityRepository = opportunityRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<RecommendationDto> getRecommendationsForStudent(Long studentId) {
        StudentProfile student = profileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found: " + studentId));

        // 1. SQL pre-filtering: Active drives matching basic batch, CGPA, backlogs
        List<Opportunity> candidates = opportunityRepository.findEligibleBaseOpportunities(
                LocalDate.now(),
                student.getGraduationYear(),
                student.getCgpa(),
                student.getActiveBacklogs()
        );

        // Filter out drives the student has already applied to
        List<Opportunity> unapplied = candidates.stream()
                .filter(opp -> !applicationRepository.existsByStudentIdAndOpportunityId(studentId, opp.getId()))
                .filter(opp -> isBranchAllowed(student.getBranch(), opp.getAllowedBranches()))
                .collect(Collectors.toList());

        Set<String> studentSkills = parseCsvToSet(student.getSkills());
        List<String> studentInterests = parseCsvToList(student.getCareerInterests());

        List<RecommendationDto> scoredRecommendations = new ArrayList<>();

        for (Opportunity opp : unapplied) {
            Set<String> oppSkills = parseCsvToSet(opp.getRequiredSkills());

            // 2. Skill Overlap Ratio (Max 40 pts)
            int matchedSkillCount = 0;
            for (String os : oppSkills) {
                if (studentSkills.contains(os)) {
                    matchedSkillCount++;
                }
            }
            double skillOverlapRatio = oppSkills.isEmpty() ? 1.0 : ((double) matchedSkillCount / oppSkills.size());
            double skillScore = skillOverlapRatio * 40.0;
            double skillPercentage = Math.round(skillOverlapRatio * 1000.0) / 10.0;

            // 3. Career Interest Match (Max 35 pts)
            boolean interestMatched = false;
            String matchedInterestWord = "";
            String jobContent = (opp.getTitle() + " " + opp.getDescription()).toLowerCase();

            for (String interest : studentInterests) {
                if (!interest.isEmpty() && jobContent.contains(interest.toLowerCase())) {
                    interestMatched = true;
                    matchedInterestWord = interest;
                    break;
                }
            }
            double interestScore = interestMatched ? 35.0 : 10.0; // baseline 10 pts even if no direct keyword match

            // 4. CGPA Clearance Margin (Max 25 pts)
            // The more comfortable the student is above min CGPA, the stronger the recommendation
            double cgpaMargin = student.getCgpa() - opp.getMinCgpa();
            double cgpaScore = Math.min(25.0, 15.0 + (cgpaMargin * 5.0));
            if (cgpaScore < 0) cgpaScore = 0.0;

            double totalAffinity = Math.min(100.0, Math.round((skillScore + interestScore + cgpaScore) * 10.0) / 10.0);

            // Construct human-readable reasoning
            StringBuilder reason = new StringBuilder();
            if (interestMatched) {
                reason.append("Directly aligns with your interest in '").append(matchedInterestWord).append("'. ");
            }
            reason.append(String.format("%.0f%% of required skills matched (%d/%d). ",
                    skillPercentage, matchedSkillCount, oppSkills.size()));
            reason.append(String.format("Academic margin: +%.2f CGPA above minimum cutoff.", cgpaMargin));

            scoredRecommendations.add(new RecommendationDto(
                    opp.getId(),
                    opp.getCompanyName(),
                    opp.getTitle(),
                    opp.getLocation(),
                    opp.getPackageDetails(),
                    totalAffinity,
                    skillPercentage,
                    interestMatched,
                    reason.toString()
            ));
        }

        // Sort descending by highest affinity score
        scoredRecommendations.sort((a, b) -> Double.compare(b.getAffinityScore(), a.getAffinityScore()));

        return scoredRecommendations;
    }

    private boolean isBranchAllowed(String studentBranch, String allowedBranches) {
        if (allowedBranches == null || studentBranch == null) return true;
        return Arrays.stream(allowedBranches.split(","))
                .map(String::trim)
                .anyMatch(b -> b.equalsIgnoreCase(studentBranch.trim()));
    }

    private Set<String> parseCsvToSet(String text) {
        if (text == null || text.trim().isEmpty()) return Collections.emptySet();
        return Arrays.stream(text.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private List<String> parseCsvToList(String text) {
        if (text == null || text.trim().isEmpty()) return Collections.emptyList();
        return Arrays.stream(text.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
