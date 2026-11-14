package com.campusconnect.dto;

import com.campusconnect.entity.JobType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class OpportunityDto {

    private Long id;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Job type is required (FULL_TIME or INTERNSHIP)")
    private JobType jobType;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Package/stipend details are required")
    private String packageDetails;

    @NotNull(message = "Minimum CGPA is required")
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double minCgpa;

    @NotBlank(message = "Allowed branches are required (comma-separated)")
    private String allowedBranches;

    @NotNull(message = "Graduation year is required")
    private Integer graduationYear;

    @NotNull(message = "Max allowed backlogs is required")
    @Min(0)
    private Integer maxBacklogs;

    @NotBlank(message = "Required skills are required (comma-separated)")
    private String requiredSkills;

    @NotNull(message = "Application deadline is required")
    private LocalDate applicationDeadline;

    public OpportunityDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        this.packageDetails = packageDetails;
    }

    public Double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(Double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public String getAllowedBranches() {
        return allowedBranches;
    }

    public void setAllowedBranches(String allowedBranches) {
        this.allowedBranches = allowedBranches;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Integer getMaxBacklogs() {
        return maxBacklogs;
    }

    public void setMaxBacklogs(Integer maxBacklogs) {
        this.maxBacklogs = maxBacklogs;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }
}
