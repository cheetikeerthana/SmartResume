package com.smartresume.service;

import com.smartresume.dao.EducationDAO;
import com.smartresume.model.Education;

import java.util.List;

public class EducationService {

    private final EducationDAO educationDAO;

    public EducationService() {
        this.educationDAO = new EducationDAO();
    }

    public boolean createEducation(Education education) {

        if (education == null || education.getResumeId() <= 0) {
            return false;
        }

        if (education.getInstitution() == null
                || education.getInstitution().isBlank()) {
            return false;
        }

        if (education.getDegree() == null
                || education.getDegree().isBlank()) {
            return false;
        }

        return educationDAO.createEducation(education);
    }

    public List<Education> getEducationByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return educationDAO.getEducationByResumeId(resumeId);
    }

    public boolean updateEducation(Education education) {

        if (education == null
                || education.getId() <= 0
                || education.getResumeId() <= 0) {
            return false;
        }

        if (education.getInstitution() == null
                || education.getInstitution().isBlank()) {
            return false;
        }

        if (education.getDegree() == null
                || education.getDegree().isBlank()) {
            return false;
        }

        return educationDAO.updateEducation(education);
    }

    public boolean deleteEducation(int educationId, int resumeId) {

        if (educationId <= 0 || resumeId <= 0) {
            return false;
        }

        return educationDAO.deleteEducation(educationId, resumeId);
    }
}