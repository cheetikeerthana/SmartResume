package com.smartresume.dao;

import com.smartresume.model.Resume;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResumeDAO {

    // Create a new resume
    public boolean createResume(Resume resume) {

        String sql = """
                INSERT INTO resumes
                (user_id, resume_name, template_name, summary)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resume.getUserId());
            statement.setString(2, resume.getResumeName());
            statement.setString(3, resume.getTemplateName());
            statement.setString(4, resume.getSummary());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find a resume by ID
    public Resume getResumeById(int resumeId) {

        String sql = """
                SELECT id, user_id, resume_name,
                       template_name, summary
                FROM resumes
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResume(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get all resumes belonging to a user
    public List<Resume> getResumesByUserId(int userId) {

        List<Resume> resumes = new ArrayList<>();

        String sql = """
                SELECT id, user_id, resume_name,
                       template_name, summary
                FROM resumes
                WHERE user_id = ?
                ORDER BY updated_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    resumes.add(mapResume(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resumes;
    }

    // Update an existing resume
    public boolean updateResume(Resume resume) {

        String sql = """
                UPDATE resumes
                SET resume_name = ?,
                    template_name = ?,
                    summary = ?
                WHERE id = ?
                AND user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, resume.getResumeName());
            statement.setString(2, resume.getTemplateName());
            statement.setString(3, resume.getSummary());
            statement.setInt(4, resume.getId());
            statement.setInt(5, resume.getUserId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a resume
    public boolean deleteResume(int resumeId, int userId) {

        String sql = """
                DELETE FROM resumes
                WHERE id = ?
                AND user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert a database row into a Resume object
    private Resume mapResume(ResultSet resultSet) throws SQLException {

        Resume resume = new Resume();

        resume.setId(resultSet.getInt("id"));
        resume.setUserId(resultSet.getInt("user_id"));
        resume.setResumeName(resultSet.getString("resume_name"));
        resume.setTemplateName(resultSet.getString("template_name"));
        resume.setSummary(resultSet.getString("summary"));

        return resume;
    }
}
