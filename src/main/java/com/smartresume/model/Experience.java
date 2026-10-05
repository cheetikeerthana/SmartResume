package com.smartresume.model;

import java.time.LocalDate;

public class Experience {

    private int id;
    private int resumeId;
    private String company;
    private String jobTitle;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public Experience() {
    }

    public Experience(int id, int resumeId, String company,
                      String jobTitle, String location,
                      LocalDate startDate, LocalDate endDate,
                      String description) {

        this.id = id;
        this.resumeId = resumeId;
        this.company = company;
        this.jobTitle = jobTitle;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
