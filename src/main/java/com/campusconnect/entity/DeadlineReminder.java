package com.campusconnect.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deadline_reminders")
public class DeadlineReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long opportunityId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String opportunityTitle;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private String studentEmail;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime reminderSentAt;

    public DeadlineReminder() {
    }

    public DeadlineReminder(Long opportunityId, String companyName, String opportunityTitle,
                            Long studentId, String studentEmail, String message) {
        this.opportunityId = opportunityId;
        this.companyName = companyName;
        this.opportunityTitle = opportunityTitle;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.message = message;
        this.reminderSentAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getReminderSentAt() {
        return reminderSentAt;
    }

    public void setReminderSentAt(LocalDateTime reminderSentAt) {
        this.reminderSentAt = reminderSentAt;
    }
}
