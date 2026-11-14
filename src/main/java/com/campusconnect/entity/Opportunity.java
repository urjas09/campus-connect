package com.campusconnect.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunities")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String packageDetails; // e.g. "12 LPA" or "40,000 / month"

    @Column(nullable = false)
    private Double minCgpa;

    @Column(nullable = false)
    private String allowedBranches; // comma-separated e.g. "CSE, IT, ECE"

    @Column(nullable = false)
    private Integer graduationYear;

    @Column(nullable = false)
    private Integer maxBacklogs;

    @Column(length = 1000, nullable = false)
    private String requiredSkills; // comma-separated e.g. "Java, Spring Boot, MySQL"

    @Column(nullable = false)
    private LocalDate applicationDeadline;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Opportunity() {
    }

    public Opportunity(String companyName, String title, String description, JobType jobType,
                       String location, String packageDetails, Double minCgpa,
                       String allowedBranches, Integer graduationYear, Integer maxBacklogs,
                       String requiredSkills, LocalDate applicationDeadline) {
        this.companyName = companyName;
        this.title = title;
        this.description = description;
        this.jobType = jobType;
        this.location = location;
        this.packageDetails = packageDetails;
        this.minCgpa = minCgpa;
        this.allowedBranches = allowedBranches;
        this.graduationYear = graduationYear;
        this.maxBacklogs = maxBacklogs;
        this.requiredSkills = requiredSkills;
        this.applicationDeadline = applicationDeadline;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
