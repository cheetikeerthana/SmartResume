package com.smartresume.dao;

import com.smartresume.model.Project;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // Add project
    public boolean createProject(Project project) {

        String sql = """
                INSERT INTO projects
                (resume_id, title, description, technologies,
                 github_url, live_url, start_date, end_date)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, project.getResumeId());
            statement.setString(2, project.getTitle());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getTechnologies());
            statement.setString(5, project.getGithubUrl());
            statement.setString(6, project.getLiveUrl());

            if (project.getStartDate() != null) {
                statement.setDate(7, Date.valueOf(project.getStartDate()));
            } else {
                statement.setNull(7, java.sql.Types.DATE);
            }

            if (project.getEndDate() != null) {
                statement.setDate(8, Date.valueOf(project.getEndDate()));
            } else {
                statement.setNull(8, java.sql.Types.DATE);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all projects for a resume
    public List<Project> getProjectsByResumeId(int resumeId) {

        List<Project> projects = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, title, description,
                       technologies, github_url, live_url,
                       start_date, end_date
                FROM projects
                WHERE resume_id = ?
                ORDER BY start_date
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    projects.add(mapProject(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    // Update project
    public boolean updateProject(Project project) {

        String sql = """
                UPDATE projects
                SET title = ?,
                    description = ?,
                    technologies = ?,
                    github_url = ?,
                    live_url = ?,
                    start_date = ?,
                    end_date = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setString(3, project.getTechnologies());
            statement.setString(4, project.getGithubUrl());
            statement.setString(5, project.getLiveUrl());

            if (project.getStartDate() != null) {
                statement.setDate(6, Date.valueOf(project.getStartDate()));
            } else {
                statement.setNull(6, java.sql.Types.DATE);
            }

            if (project.getEndDate() != null) {
                statement.setDate(7, Date.valueOf(project.getEndDate()));
            } else {
                statement.setNull(7, java.sql.Types.DATE);
            }

            statement.setInt(8, project.getId());
            statement.setInt(9, project.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete project
    public boolean deleteProject(int projectId, int resumeId) {

        String sql = """
                DELETE FROM projects
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projectId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert database row into Project object
    private Project mapProject(ResultSet resultSet) throws SQLException {

        Project project = new Project();

        project.setId(resultSet.getInt("id"));
        project.setResumeId(resultSet.getInt("resume_id"));
        project.setTitle(resultSet.getString("title"));
        project.setDescription(resultSet.getString("description"));
        project.setTechnologies(resultSet.getString("technologies"));
        project.setGithubUrl(resultSet.getString("github_url"));
        project.setLiveUrl(resultSet.getString("live_url"));

        Date startDate = resultSet.getDate("start_date");
        Date endDate = resultSet.getDate("end_date");

        if (startDate != null) {
            project.setStartDate(startDate.toLocalDate());
        }

        if (endDate != null) {
            project.setEndDate(endDate.toLocalDate());
        }

        return project;
    }
}
