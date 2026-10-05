package com.smartresume.model;

public class Language {

    private int id;
    private int resumeId;
    private String language;
    private String proficiency;

    public Language() {
    }

    public Language(int id, int resumeId, String language,
                    String proficiency) {

        this.id = id;
        this.resumeId = resumeId;
        this.language = language;
        this.proficiency = proficiency;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}