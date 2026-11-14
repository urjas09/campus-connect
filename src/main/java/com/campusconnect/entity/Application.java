package com.campusconnect.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "opportunity_id"})
})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id", nullable = false)
    private Opportunity opportunity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus currentStatus;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp ASC")
    private List<ApplicationStatusHistory> history = new ArrayList<>();

    public Application() {
    }

    public Application(StudentProfile student, Opportunity opportunity) {
        this.student = student;
        this.opportunity = opportunity;
        this.currentStatus = ApplicationStatus.APPLIED;
        this.appliedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void addStatusHistory(ApplicationStatus status, String remarks, String updatedBy) {
        this.currentStatus = status;
        this.updatedAt = LocalDateTime.now();
        ApplicationStatusHistory entry = new ApplicationStatusHistory(this, status, remarks, updatedBy);
        this.history.add(entry);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentProfile getStudent() {
        return student;
    }

    public void setStudent(StudentProfile student) {
        this.student = student;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
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

    public List<ApplicationStatusHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ApplicationStatusHistory> history) {
        this.history = history;
    }
}
