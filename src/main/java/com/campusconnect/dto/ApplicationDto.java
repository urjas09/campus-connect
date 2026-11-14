package com.campusconnect.dto;

import com.campusconnect.entity.ApplicationStatus;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationDto {

    private Long id;
    private Long studentId;
    private String studentName;
    private String studentRoll;
    private String studentBranch;
    private Double studentCgpa;
    private Long opportunityId;
    private String companyName;
    private String opportunityTitle;
    private ApplicationStatus currentStatus;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
    private List<StatusHistoryDto> history;

    public ApplicationDto() {
    }

    public static class StatusHistoryDto {
        private Long id;
        private ApplicationStatus status;
        private LocalDateTime timestamp;
        private String remarks;
        private String updatedBy;

        public StatusHistoryDto() {
        }

        public StatusHistoryDto(Long id, ApplicationStatus status, LocalDateTime timestamp, String remarks, String updatedBy) {
            this.id = id;
            this.status = status;
            this.timestamp = timestamp;
            this.remarks = remarks;
            this.updatedBy = updatedBy;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public ApplicationStatus getStatus() {
            return status;
        }

        public void setStatus(ApplicationStatus status) {
            this.status = status;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getStudentBranch() {
        return studentBranch;
    }

    public void setStudentBranch(String studentBranch) {
        this.studentBranch = studentBranch;
    }

    public Double getStudentCgpa() {
        return studentCgpa;
    }

    public void setStudentCgpa(Double studentCgpa) {
        this.studentCgpa = studentCgpa;
    }

    public Long getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Long opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOpportunityTitle() {
        return opportunityTitle;
    }

    public void setOpportunityTitle(String opportunityTitle) {
        this.opportunityTitle = opportunityTitle;
    }

    public ApplicationStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ApplicationStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<StatusHistoryDto> getHistory() {
        return history;
    }

    public void setHistory(List<StatusHistoryDto> history) {
        this.history = history;
    }
}
