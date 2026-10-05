package com.smartresume.model;

public class Resume {

    private int id;
    private int userId;
    private String resumeName;
    private String templateName;
    private String summary;

    public Resume() {
    }

    public Resume(int id, int userId, String resumeName,
                  String templateName, String summary) {
        this.id = id;
        this.userId = userId;
        this.resumeName = resumeName;
        this.templateName = templateName;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}