package com.campusconnect.dto;

import java.util.List;

public class SkillGapAnalysisDto {

    private Long studentId;
    private Long opportunityId;
    private String opportunityTitle;
    private String companyName;
    private double skillCoveragePercentage;
    private int totalRequiredSkills;
    private int availableCount;
    private int needsImprovementCount;
    private int missingCount;
    private List<SkillItemDto> skillsBreakdown;

    public SkillGapAnalysisDto() {
    }

    public static class SkillItemDto {
        private String skillName;
        private SkillStatus status;
        private String reason;
        private String learningTip;

        public SkillItemDto() {
        }

        public SkillItemDto(String skillName, SkillStatus status, String reason, String learningTip) {
            this.skillName = skillName;
            this.status = status;
            this.reason = reason;
            this.learningTip = learningTip;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public SkillStatus getStatus() {
            return status;
        }

        public void setStatus(SkillStatus status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getLearningTip() {
            return learningTip;
        }

        public void setLearningTip(String learningTip) {
            this.learningTip = learningTip;
        }
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Long opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityTitle() {
        return opportunityTitle;
    }

    public void setOpportunityTitle(String opportunityTitle) {
        this.opportunityTitle = opportunityTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getSkillCoveragePercentage() {
        return skillCoveragePercentage;
    }

    public void setSkillCoveragePercentage(double skillCoveragePercentage) {
        this.skillCoveragePercentage = skillCoveragePercentage;
    }

    public int getTotalRequiredSkills() {
        return totalRequiredSkills;
    }

    public void setTotalRequiredSkills(int totalRequiredSkills) {
        this.totalRequiredSkills = totalRequiredSkills;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getNeedsImprovementCount() {
        return needsImprovementCount;
    }

    public void setNeedsImprovementCount(int needsImprovementCount) {
        this.needsImprovementCount = needsImprovementCount;
    }

    public int getMissingCount() {
        return missingCount;
    }

    public void setMissingCount(int missingCount) {
        this.missingCount = missingCount;
    }

    public List<SkillItemDto> getSkillsBreakdown() {
        return skillsBreakdown;
    }

    public void setSkillsBreakdown(List<SkillItemDto> skillsBreakdown) {
        this.skillsBreakdown = skillsBreakdown;
    }
}
