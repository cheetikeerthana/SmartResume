package com.smartresume.service;

import com.smartresume.dao.ResumeDAO;
import com.smartresume.model.Resume;

import java.util.List;

public class ResumeService {

    private final ResumeDAO resumeDAO;

    public ResumeService() {
        this.resumeDAO = new ResumeDAO();
    }

    public boolean createResume(Resume resume) {

        if (resume == null || resume.getUserId() <= 0) {
            return false;
        }

        if (resume.getResumeName() == null
                || resume.getResumeName().isBlank()) {
            return false;
        }

        return resumeDAO.createResume(resume);
    }

    public Resume getResumeById(int resumeId, int userId) {

        if (resumeId <= 0 || userId <= 0) {
            return null;
        }

        Resume resume = resumeDAO.getResumeById(resumeId);

        if (resume == null || resume.getUserId() != userId) {
            return null;
        }

        return resume;
    }

    public List<Resume> getResumesByUserId(int userId) {

        if (userId <= 0) {
            return List.of();
        }

        return resumeDAO.getResumesByUserId(userId);
    }

    public boolean updateResume(Resume resume, int userId) {

        if (resume == null || resume.getId() <= 0
                || resume.getUserId() != userId) {
            return false;
        }

        if (resume.getResumeName() == null
                || resume.getResumeName().isBlank()) {
            return false;
        }

        return resumeDAO.updateResume(resume);
    }

    public boolean deleteResume(int resumeId, int userId) {

        if (resumeId <= 0 || userId <= 0) {
            return false;
        }

        return resumeDAO.deleteResume(resumeId, userId);
    }
}