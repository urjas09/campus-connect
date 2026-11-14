package com.campusconnect.dto;

public class RecommendationDto {

    private Long opportunityId;
    private String companyName;
    private String title;
    private String location;
    private String packageDetails;
    private double affinityScore; // 0 to 100
    private double skillOverlapPercentage;
    private boolean interestMatched;
    private String recommendationReason;

    public RecommendationDto() {
    }

    public RecommendationDto(Long opportunityId, String companyName, String title, String location,
                              String packageDetails, double affinityScore,
                              double skillOverlapPercentage, boolean interestMatched,
                              String recommendationReason) {
        this.opportunityId = opportunityId;
        this.companyName = companyName;
        this.title = title;
        this.location = location;
        this.packageDetails = packageDetails;
        this.affinityScore = affinityScore;
        this.skillOverlapPercentage = skillOverlapPercentage;
        this.interestMatched = interestMatched;
        this.recommendationReason = recommendationReason;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public double getAffinityScore() {
        return affinityScore;
    }

    public void setAffinityScore(double affinityScore) {
        this.affinityScore = affinityScore;
    }

    public double getSkillOverlapPercentage() {
        return skillOverlapPercentage;
    }

    public void setSkillOverlapPercentage(double skillOverlapPercentage) {
        this.skillOverlapPercentage = skillOverlapPercentage;
    }

    public boolean isInterestMatched() {
        return interestMatched;
    }

    public void setInterestMatched(boolean interestMatched) {
        this.interestMatched = interestMatched;
    }

    public String getRecommendationReason() {
        return recommendationReason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }
}
