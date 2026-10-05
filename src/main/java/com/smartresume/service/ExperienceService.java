package com.smartresume.service;

import com.smartresume.dao.ExperienceDAO;
import com.smartresume.model.Experience;

import java.util.List;

public class ExperienceService {

    private final ExperienceDAO experienceDAO;

    public ExperienceService() {
        this.experienceDAO = new ExperienceDAO();
    }

    public boolean createExperience(Experience experience) {

        if (experience == null || experience.getResumeId() <= 0) {
            return false;
        }

        if (experience.getCompany() == null
                || experience.getCompany().isBlank()) {
            return false;
        }

        if (experience.getJobTitle() == null
                || experience.getJobTitle().isBlank()) {
            return false;
        }

        return experienceDAO.createExperience(experience);
    }

    public List<Experience> getExperienceByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return experienceDAO.getExperienceByResumeId(resumeId);
    }

    public boolean updateExperience(Experience experience) {

        if (experience == null
                || experience.getId() <= 0
                || experience.getResumeId() <= 0) {
            return false;
        }

        if (experience.getCompany() == null
                || experience.getCompany().isBlank()) {
            return false;
        }

        if (experience.getJobTitle() == null
                || experience.getJobTitle().isBlank()) {
            return false;
        }

        return experienceDAO.updateExperience(experience);
    }

    public boolean deleteExperience(int experienceId, int resumeId) {

        if (experienceId <= 0 || resumeId <= 0) {
            return false;
        }

        return experienceDAO.deleteExperience(experienceId, resumeId);
    }
}