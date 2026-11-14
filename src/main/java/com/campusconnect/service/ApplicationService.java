package com.campusconnect.service;

import com.campusconnect.dto.ApplicationDto;
import com.campusconnect.dto.UpdateStatusRequest;
import com.campusconnect.entity.*;
import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.repository.ApplicationRepository;
import com.campusconnect.repository.OpportunityRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentProfileRepository profileRepository;
    private final OpportunityRepository opportunityRepository;
    private final EligibilityEngineService eligibilityService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              StudentProfileRepository profileRepository,
                              OpportunityRepository opportunityRepository,
                              EligibilityEngineService eligibilityService) {
        this.applicationRepository = applicationRepository;
        this.profileRepository = profileRepository;
        this.opportunityRepository = opportunityRepository;
        this.eligibilityService = eligibilityService;
    }

    @Transactional
    public ApplicationDto apply(Long studentId, Long opportunityId) {
        StudentProfile student = profileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found: " + studentId));

        Opportunity opp = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new ResourceNotFoundException("Opportunity not found: " + opportunityId));

        // 1. Check if application deadline passed
        if (opp.getApplicationDeadline().isBefore(LocalDate.now())) {
            throw new BadRequestException("Application deadline has already passed on " + opp.getApplicationDeadline());
        }

        // 2. Prevent duplicate applications
        if (applicationRepository.existsByStudentIdAndOpportunityId(studentId, opportunityId)) {
            throw new BadRequestException("You have already applied for this opportunity");
        }

        // 3. Verify hard eligibility
        var eval = eligibilityService.evaluateEligibility(studentId, opportunityId);
        if (!eval.isEligible()) {
            throw new BadRequestException("Cannot apply: You do not meet the minimum criteria. Missing: " +
                    String.join("; ", eval.getMissingCriteria()));
        }

        // Create application and initial history record
        Application application = new Application(student, opp);
        application.addStatusHistory(
                ApplicationStatus.APPLIED,
                "Application submitted successfully by student",
                student.getUser().getFullName()
        );

        Application saved = applicationRepository.save(application);
        return mapToDto(saved);
    }

    @Transactional
    public ApplicationDto updateStatus(Long applicationId, UpdateStatusRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        String updater = request.getUpdatedBy() != null ? request.getUpdatedBy() : "Placement Cell";
        String remarks = request.getRemarks() != null ? request.getRemarks() : "Status updated to " + request.getStatus();

        application.addStatusHistory(request.getStatus(), remarks, updater);
        Application updated = applicationRepository.save(application);
        return mapToDto(updated);
    }

    public List<ApplicationDto> getStudentApplications(Long studentId) {
        return applicationRepository.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getOpportunityApplications(Long opportunityId) {
        return applicationRepository.findByOpportunityId(opportunityId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ApplicationDto getApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));
        return mapToDto(application);
    }

    private ApplicationDto mapToDto(Application a) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(a.getId());
        dto.setStudentId(a.getStudent().getId());
        dto.setStudentName(a.getStudent().getUser().getFullName());
        dto.setStudentRoll(a.getStudent().getRollNumber());
        dto.setStudentBranch(a.getStudent().getBranch());
        dto.setStudentCgpa(a.getStudent().getCgpa());
        dto.setOpportunityId(a.getOpportunity().getId());
        dto.setCompanyName(a.getOpportunity().getCompanyName());
        dto.setOpportunityTitle(a.getOpportunity().getTitle());
        dto.setCurrentStatus(a.getCurrentStatus());
        dto.setAppliedAt(a.getAppliedAt());
        dto.setUpdatedAt(a.getUpdatedAt());

        List<ApplicationDto.StatusHistoryDto> historyList = a.getHistory().stream()
                .map(h -> new ApplicationDto.StatusHistoryDto(
                        h.getId(),
                        h.getStatus(),
                        h.getTimestamp(),
                        h.getRemarks(),
                        h.getUpdatedBy()
                ))
                .collect(Collectors.toList());
        dto.setHistory(historyList);

        return dto;
    }
}
