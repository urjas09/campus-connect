package com.campusconnect.dto;

import java.util.List;

public class EligibilityResultDto {

    private Long opportunityId;
    private String companyName;
    private String opportunityTitle;
    private boolean eligible;
    private double matchPercentage;
    private List<String> passedCriteria;
    private List<String> missingCriteria;
    private List<String> missingSkills;

    public EligibilityResultDto() {
    }

    public EligibilityResultDto(Long opportunityId, String companyName, String opportunityTitle,
                                boolean eligible, double matchPercentage,
                                List<String> passedCriteria, List<String> missingCriteria,
                                List<String> missingSkills) {
        this.opportunityId = opportunityId;
        this.companyName = companyName;
        this.opportunityTitle = opportunityTitle;
        this.eligible = eligible;
        this.matchPercentage = matchPercentage;
        this.passedCriteria = passedCriteria;
        this.missingCriteria = missingCriteria;
        this.missingSkills = missingSkills;
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

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public List<String> getPassedCriteria() {
        return passedCriteria;
    }

    public void setPassedCriteria(List<String> passedCriteria) {
        this.passedCriteria = passedCriteria;
    }

    public List<String> getMissingCriteria() {
        return missingCriteria;
    }

    public void setMissingCriteria(List<String> missingCriteria) {
        this.missingCriteria = missingCriteria;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }
}
