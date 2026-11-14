package com.campusconnect.dto;

import com.campusconnect.entity.ApplicationStatus;
import java.util.Map;

public class PlacementAnalyticsDto {

    private long totalStudents;
    private long totalOpportunities;
    private long activeOpportunities;
    private long totalApplications;
    private long totalSelected;
    private long totalRejected;
    private double overallSelectionRate; // percentage
    private Map<ApplicationStatus, Long> stageDistribution;
    private Map<String, Long> branchWiseStudentCount;
    private Map<String, Long> branchWiseSelectedCount;

    public PlacementAnalyticsDto() {
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalOpportunities() {
        return totalOpportunities;
    }

    public void setTotalOpportunities(long totalOpportunities) {
        this.totalOpportunities = totalOpportunities;
    }

    public long getActiveOpportunities() {
        return activeOpportunities;
    }

    public void setActiveOpportunities(long activeOpportunities) {
        this.activeOpportunities = activeOpportunities;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getTotalSelected() {
        return totalSelected;
    }

    public void setTotalSelected(long totalSelected) {
        this.totalSelected = totalSelected;
    }

    public long getTotalRejected() {
        return totalRejected;
    }

    public void setTotalRejected(long totalRejected) {
        this.totalRejected = totalRejected;
    }

    public double getOverallSelectionRate() {
        return overallSelectionRate;
    }

    public void setOverallSelectionRate(double overallSelectionRate) {
        this.overallSelectionRate = overallSelectionRate;
    }

    public Map<ApplicationStatus, Long> getStageDistribution() {
        return stageDistribution;
    }

    public void setStageDistribution(Map<ApplicationStatus, Long> stageDistribution) {
        this.stageDistribution = stageDistribution;
    }

    public Map<String, Long> getBranchWiseStudentCount() {
        return branchWiseStudentCount;
    }

    public void setBranchWiseStudentCount(Map<String, Long> branchWiseStudentCount) {
        this.branchWiseStudentCount = branchWiseStudentCount;
    }

    public Map<String, Long> getBranchWiseSelectedCount() {
        return branchWiseSelectedCount;
    }

    public void setBranchWiseSelectedCount(Map<String, Long> branchWiseSelectedCount) {
        this.branchWiseSelectedCount = branchWiseSelectedCount;
    }
}
