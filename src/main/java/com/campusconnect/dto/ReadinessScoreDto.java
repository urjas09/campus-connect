package com.campusconnect.dto;

import java.util.List;

public class ReadinessScoreDto {

    private Long studentId;
    private String studentName;
    private double overallScore; // 0 to 100
    private String readinessBand; // e.g. "Placement Ready", "Competitive"
    private double academicScore; // max 30
    private double profileCompletenessScore; // max 20
    private double skillBreadthScore; // max 30
    private double pipelineMomentumScore; // max 20
    private List<String> scoreBreakdown;
    private List<String> improvementActionItems;

    public ReadinessScoreDto() {
    }

    public ReadinessScoreDto(Long studentId, String studentName, double overallScore, String readinessBand,
                             double academicScore, double profileCompletenessScore,
                             double skillBreadthScore, double pipelineMomentumScore,
                             List<String> scoreBreakdown, List<String> improvementActionItems) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.overallScore = overallScore;
        this.readinessBand = readinessBand;
        this.academicScore = academicScore;
        this.profileCompletenessScore = profileCompletenessScore;
        this.skillBreadthScore = skillBreadthScore;
        this.pipelineMomentumScore = pipelineMomentumScore;
        this.scoreBreakdown = scoreBreakdown;
        this.improvementActionItems = improvementActionItems;
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

    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
    }

    public String getReadinessBand() {
        return readinessBand;
    }

    public void setReadinessBand(String readinessBand) {
        this.readinessBand = readinessBand;
    }

    public double getAcademicScore() {
        return academicScore;
    }

    public void setAcademicScore(double academicScore) {
        this.academicScore = academicScore;
    }

    public double getProfileCompletenessScore() {
        return profileCompletenessScore;
    }

    public void setProfileCompletenessScore(double profileCompletenessScore) {
        this.profileCompletenessScore = profileCompletenessScore;
    }

    public double getSkillBreadthScore() {
        return skillBreadthScore;
    }

    public void setSkillBreadthScore(double skillBreadthScore) {
        this.skillBreadthScore = skillBreadthScore;
    }

    public double getPipelineMomentumScore() {
        return pipelineMomentumScore;
    }

    public void setPipelineMomentumScore(double pipelineMomentumScore) {
        this.pipelineMomentumScore = pipelineMomentumScore;
    }

    public List<String> getScoreBreakdown() {
        return scoreBreakdown;
    }

    public void setScoreBreakdown(List<String> scoreBreakdown) {
        this.scoreBreakdown = scoreBreakdown;
    }

    public List<String> getImprovementActionItems() {
        return improvementActionItems;
    }

    public void setImprovementActionItems(List<String> improvementActionItems) {
        this.improvementActionItems = improvementActionItems;
    }
}
