package com.campusconnect.dto;

import com.campusconnect.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {

    @NotNull(message = "New status is required")
    private ApplicationStatus status;

    private String remarks;
    private String updatedBy;

    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(ApplicationStatus status, String remarks, String updatedBy) {
        this.status = status;
        this.remarks = remarks;
        this.updatedBy = updatedBy;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
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
