package com.smartresume.dao;

import com.smartresume.model.Experience;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExperienceDAO {

    // Add experience
    public boolean createExperience(Experience experience) {

        String sql = """
                INSERT INTO experience
                (resume_id, company, job_title, location,
                 start_date, end_date, description)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, experience.getResumeId());
            statement.setString(2, experience.getCompany());
            statement.setString(3, experience.getJobTitle());
            statement.setString(4, experience.getLocation());

            if (experience.getStartDate() != null) {
                statement.setDate(5, Date.valueOf(experience.getStartDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            if (experience.getEndDate() != null) {
                statement.setDate(6, Date.valueOf(experience.getEndDate()));
            } else {
                statement.setNull(6, java.sql.Types.DATE);
            }

            statement.setString(7, experience.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all experience entries for a resume
    public List<Experience> getExperienceByResumeId(int resumeId) {

        List<Experience> experiences = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, company, job_title,
                       location, start_date, end_date, description
                FROM experience
                WHERE resume_id = ?
                ORDER BY start_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    experiences.add(mapExperience(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return experiences;
    }

    // Update experience
    public boolean updateExperience(Experience experience) {

        String sql = """
                UPDATE experience
                SET company = ?,
                    job_title = ?,
                    location = ?,
                    start_date = ?,
                    end_date = ?,
                    description = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, experience.getCompany());
            statement.setString(2, experience.getJobTitle());
            statement.setString(3, experience.getLocation());

            if (experience.getStartDate() != null) {
                statement.setDate(4, Date.valueOf(experience.getStartDate()));
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }

            if (experience.getEndDate() != null) {
                statement.setDate(5, Date.valueOf(experience.getEndDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            statement.setString(6, experience.getDescription());
            statement.setInt(7, experience.getId());
            statement.setInt(8, experience.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete experience
    public boolean deleteExperience(int experienceId, int resumeId) {

        String sql = """
                DELETE FROM experience
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, experienceId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert database row into Experience object
    private Experience mapExperience(ResultSet resultSet)
            throws SQLException {

        Experience experience = new Experience();

        experience.setId(resultSet.getInt("id"));
        experience.setResumeId(resultSet.getInt("resume_id"));
        experience.setCompany(resultSet.getString("company"));
        experience.setJobTitle(resultSet.getString("job_title"));
        experience.setLocation(resultSet.getString("location"));

        Date startDate = resultSet.getDate("start_date");
        Date endDate = resultSet.getDate("end_date");

        if (startDate != null) {
            experience.setStartDate(startDate.toLocalDate());
        }

        if (endDate != null) {
            experience.setEndDate(endDate.toLocalDate());
        }

        experience.setDescription(resultSet.getString("description"));

        return experience;
    }
}
