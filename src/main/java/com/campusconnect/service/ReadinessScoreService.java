package com.campusconnect.service;

import com.campusconnect.dto.ReadinessScoreDto;
import com.campusconnect.entity.Application;
import com.campusconnect.entity.ApplicationStatus;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.ApplicationRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReadinessScoreService {

    private final StudentProfileRepository profileRepository;
    private final ApplicationRepository applicationRepository;

    public ReadinessScoreService(StudentProfileRepository profileRepository,
                                 ApplicationRepository applicationRepository) {
        this.profileRepository = profileRepository;
        this.applicationRepository = applicationRepository;
    }

    public ReadinessScoreDto calculateReadinessScore(Long studentId) {
        StudentProfile student = profileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found: " + studentId));

        List<Application> apps = applicationRepository.findByStudentId(studentId);

        List<String> breakdown = new ArrayList<>();
        List<String> actions = new ArrayList<>();

        // 1. Academic Foundation (Max 30)
        // CGPA scaled: (cgpa / 10.0) * 20
        double cgpaScore = (student.getCgpa() / 10.0) * 20.0;
        double backlogScore = 0.0;
        if (student.getActiveBacklogs() == 0) {
            backlogScore = 10.0;
            breakdown.add(String.format("Academic: Full marks for 0 backlogs (+10 pts) and CGPA %.2f (+%.1f pts)",
                    student.getCgpa(), cgpaScore));
        } else if (student.getActiveBacklogs() == 1) {
            backlogScore = 5.0;
            breakdown.add(String.format("Academic: Partial marks for 1 backlog (+5 pts) and CGPA %.2f (+%.1f pts)",
                    student.getCgpa(), cgpaScore));
            actions.add("Clear 1 active backlog to gain +5 academic points");
        } else {
            breakdown.add(String.format("Academic: 0 pts for %d active backlogs and CGPA %.2f (+%.1f pts)",
                    student.getActiveBacklogs(), student.getCgpa(), cgpaScore));
            actions.add("Clear standing backlogs to meet baseline eligibility across top tier companies");
        }
        double academicTotal = Math.round((cgpaScore + backlogScore) * 10.0) / 10.0;

        // 2. Profile Completeness (Max 20)
        double profileTotal = 0.0;
        if (isNotBlank(student.getResumeUrl())) {
            profileTotal += 5.0;
        } else {
            actions.add("Upload your resume link (+5 pts)");
        }

        if (isNotBlank(student.getLinkedInUrl())) {
            profileTotal += 5.0;
        } else {
            actions.add("Add your LinkedIn profile URL (+5 pts)");
        }

        if (isNotBlank(student.getGitHubUrl())) {
            profileTotal += 5.0;
        } else {
            actions.add("Add your GitHub profile URL (+5 pts)");
        }

        if (isNotBlank(student.getCareerInterests())) {
            profileTotal += 5.0;
        } else {
            actions.add("Specify your targeted career interests (+5 pts)");
        }
        breakdown.add(String.format("Profile Completeness: %.0f / 20 pts from profile links and details", profileTotal));

        // 3. Technical Skill Breadth (Max 30)
        long skillCount = 0;
        if (student.getSkills() != null && !student.getSkills().trim().isEmpty()) {
            skillCount = Arrays.stream(student.getSkills().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .count();
        }

        double skillTotal = 0.0;
        if (skillCount >= 5) {
            skillTotal = 30.0;
            breakdown.add("Skill Breadth: Excellent skill coverage with " + skillCount + " listed skills (+30 pts)");
        } else if (skillCount >= 3) {
            skillTotal = 20.0;
            breakdown.add("Skill Breadth: Good core foundation with " + skillCount + " skills (+20 pts)");
            actions.add("Add at least 2 more technologies or frameworks to achieve max skill score (+10 pts)");
        } else if (skillCount >= 1) {
            skillTotal = 10.0;
            breakdown.add("Skill Breadth: Basic skills entered (" + skillCount + ") (+10 pts)");
            actions.add("Expand your skill toolkit with modern tools, databases, or frameworks (+20 pts)");
        } else {
            actions.add("Add your technical skills to your profile immediately (+30 pts)");
        }

        // 4. Recruitment Pipeline Momentum (Max 20)
        double momentumTotal = 0.0;
        int appCount = apps.size();
        if (appCount > 0) {
            momentumTotal += Math.min(appCount * 5.0, 10.0); // up to 10 for applying

            // Bonus for advancing through pipeline
            boolean reachedInterview = apps.stream().anyMatch(a ->
                    a.getCurrentStatus() == ApplicationStatus.TECHNICAL_INTERVIEW ||
                    a.getCurrentStatus() == ApplicationStatus.HR_INTERVIEW ||
                    a.getCurrentStatus() == ApplicationStatus.SELECTED);

            boolean reachedAssessment = apps.stream().anyMatch(a ->
                    a.getCurrentStatus() == ApplicationStatus.ASSESSMENT);

            if (reachedInterview) {
                momentumTotal += 10.0;
                breakdown.add("Momentum: Active participant with " + appCount + " applications & reached Interview round (+20 pts)");
            } else if (reachedAssessment) {
                momentumTotal += 5.0;
                breakdown.add("Momentum: Participated in campus drives and online assessments (+15 pts)");
                actions.add("Prepare for technical interviews to unlock maximum momentum bonus");
            } else {
                breakdown.add("Momentum: Applied to " + appCount + " drive(s) (+5 pts)");
            }
        } else {
            breakdown.add("Momentum: No applications submitted yet (0 pts)");
            actions.add("Start applying to eligible campus drives to gain placement momentum points (+20 pts)");
        }

        double overall = Math.min(100.0, Math.round((academicTotal + profileTotal + skillTotal + momentumTotal) * 10.0) / 10.0);

        // Readiness Band classification
        String band;
        if (overall >= 85.0) {
            band = "Campus Star (Placement Ready)";
        } else if (overall >= 70.0) {
            band = "Competitive Candidate";
        } else if (overall >= 50.0) {
            band = "Needs Preparation";
        } else {
            band = "Critical Action Required";
        }

        return new ReadinessScoreDto(
                student.getId(),
                student.getUser().getFullName(),
                overall,
                band,
                academicTotal,
                profileTotal,
                skillTotal,
                momentumTotal,
                breakdown,
                actions
        );
    }

    private boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
