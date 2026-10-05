package com.smartresume.model;

public class Skill {

    private int id;
    private int resumeId;
    private String skillName;
    private String skillCategory;
    private String proficiency;

    public Skill() {
    }

    public Skill(int id, int resumeId, String skillName,
                 String skillCategory, String proficiency) {

        this.id = id;
        this.resumeId = resumeId;
        this.skillName = skillName;
        this.skillCategory = skillCategory;
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

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillCategory() {
        return skillCategory;
    }

    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}