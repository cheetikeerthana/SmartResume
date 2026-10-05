package com.smartresume.model;

import java.time.LocalDate;

public class Certification {

    private int id;
    private int resumeId;
    private String name;
    private String issuingOrganization;
    private LocalDate issueDate;
    private String credentialUrl;
    private String description;

    public Certification() {
    }

    public Certification(int id, int resumeId, String name,
                         String issuingOrganization,
                         LocalDate issueDate,
                         String credentialUrl,
                         String description) {

        this.id = id;
        this.resumeId = resumeId;
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.credentialUrl = credentialUrl;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
