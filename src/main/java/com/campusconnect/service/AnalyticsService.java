package com.campusconnect.service;

import com.campusconnect.dto.PlacementAnalyticsDto;
import com.campusconnect.entity.Application;
import com.campusconnect.entity.ApplicationStatus;
import com.campusconnect.entity.StudentProfile;
import com.campusconnect.repository.ApplicationRepository;
import com.campusconnect.repository.OpportunityRepository;
import com.campusconnect.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final StudentProfileRepository profileRepository;
    private final OpportunityRepository opportunityRepository;
    private final ApplicationRepository applicationRepository;

    public AnalyticsService(StudentProfileRepository profileRepository,
                            OpportunityRepository opportunityRepository,
                            ApplicationRepository applicationRepository) {
        this.profileRepository = profileRepository;
        this.opportunityRepository = opportunityRepository;
        this.applicationRepository = applicationRepository;
    }

    public PlacementAnalyticsDto getPlacementAnalytics() {
        long totalStudents = profileRepository.count();
        long totalOpps = opportunityRepository.count();
        long activeOpps = opportunityRepository
                .findByApplicationDeadlineGreaterThanEqualOrderByApplicationDeadlineAsc(LocalDate.now())
                .size();

        List<Application> allApps = applicationRepository.findAll();
        long totalApplications = allApps.size();

        // 1. Stage Distribution
        Map<ApplicationStatus, Long> stageMap = new EnumMap<>(ApplicationStatus.class);
        for (ApplicationStatus status : ApplicationStatus.values()) {
            stageMap.put(status, 0L);
        }
        for (Application app : allApps) {
            stageMap.put(app.getCurrentStatus(), stageMap.get(app.getCurrentStatus()) + 1L);
        }

        long selectedCount = stageMap.getOrDefault(ApplicationStatus.SELECTED, 0L);
        long rejectedCount = stageMap.getOrDefault(ApplicationStatus.REJECTED, 0L);

        double selectionRate = totalApplications > 0
                ? Math.round(((double) selectedCount / totalApplications) * 1000.0) / 10.0
                : 0.0;

        // 2. Branch-wise distribution
        List<StudentProfile> students = profileRepository.findAll();
        Map<String, Long> branchTotal = students.stream()
                .collect(Collectors.groupingBy(StudentProfile::getBranch, Collectors.counting()));

        // Branch-wise selected count
        Map<String, Long> branchSelected = new HashMap<>();
        for (Application app : allApps) {
            if (app.getCurrentStatus() == ApplicationStatus.SELECTED) {
                String branch = app.getStudent().getBranch();
                branchSelected.put(branch, branchSelected.getOrDefault(branch, 0L) + 1L);
            }
        }

        PlacementAnalyticsDto dto = new PlacementAnalyticsDto();
        dto.setTotalStudents(totalStudents);
        dto.setTotalOpportunities(totalOpps);
        dto.setActiveOpportunities(activeOpps);
        dto.setTotalApplications(totalApplications);
        dto.setTotalSelected(selectedCount);
        dto.setTotalRejected(rejectedCount);
        dto.setOverallSelectionRate(selectionRate);
        dto.setStageDistribution(stageMap);
        dto.setBranchWiseStudentCount(branchTotal);
        dto.setBranchWiseSelectedCount(branchSelected);

        return dto;
    }
}
