package com.smartresume.service;

import com.smartresume.dao.ProjectDAO;
import com.smartresume.model.Project;

import java.util.List;

public class ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectService() {
        this.projectDAO = new ProjectDAO();
    }

    public boolean createProject(Project project) {

        if (project == null || project.getResumeId() <= 0) {
            return false;
        }

        if (project.getTitle() == null
                || project.getTitle().isBlank()) {
            return false;
        }

        return projectDAO.createProject(project);
    }

    public List<Project> getProjectsByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return projectDAO.getProjectsByResumeId(resumeId);
    }

    public boolean updateProject(Project project) {

        if (project == null
                || project.getId() <= 0
                || project.getResumeId() <= 0) {
            return false;
        }

        if (project.getTitle() == null
                || project.getTitle().isBlank()) {
            return false;
        }

        return projectDAO.updateProject(project);
    }

    public boolean deleteProject(int projectId, int resumeId) {

        if (projectId <= 0 || resumeId <= 0) {
            return false;
        }

        return projectDAO.deleteProject(projectId, resumeId);
    }
}