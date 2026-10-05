package com.smartresume.dao;

import com.smartresume.model.Education;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EducationDAO {

    // Add education
    public boolean createEducation(Education education) {

        String sql = """
                INSERT INTO education
                (resume_id, institution, degree, field_of_study,
                 start_year, end_year, grade, description)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, education.getResumeId());
            statement.setString(2, education.getInstitution());
            statement.setString(3, education.getDegree());
            statement.setString(4, education.getFieldOfStudy());
            statement.setInt(5, education.getStartYear());
            statement.setInt(6, education.getEndYear());
            statement.setString(7, education.getGrade());
            statement.setString(8, education.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get education by resume ID
    public List<Education> getEducationByResumeId(int resumeId) {

        List<Education> educationList = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, institution, degree,
                       field_of_study, start_year, end_year,
                       grade, description
                FROM education
                WHERE resume_id = ?
                ORDER BY start_year
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    educationList.add(mapEducation(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return educationList;
    }

    // Update education
    public boolean updateEducation(Education education) {

        String sql = """
                UPDATE education
                SET institution = ?,
                    degree = ?,
                    field_of_study = ?,
                    start_year = ?,
                    end_year = ?,
                    grade = ?,
                    description = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, education.getInstitution());
            statement.setString(2, education.getDegree());
            statement.setString(3, education.getFieldOfStudy());
            statement.setInt(4, education.getStartYear());
            statement.setInt(5, education.getEndYear());
            statement.setString(6, education.getGrade());
            statement.setString(7, education.getDescription());
            statement.setInt(8, education.getId());
            statement.setInt(9, education.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete education
    public boolean deleteEducation(int educationId, int resumeId) {

        String sql = """
                DELETE FROM education
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, educationId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert database row into Education object
    private Education mapEducation(ResultSet resultSet) throws SQLException {

        Education education = new Education();

        education.setId(resultSet.getInt("id"));
        education.setResumeId(resultSet.getInt("resume_id"));
        education.setInstitution(resultSet.getString("institution"));
        education.setDegree(resultSet.getString("degree"));
        education.setFieldOfStudy(resultSet.getString("field_of_study"));
        education.setStartYear(resultSet.getInt("start_year"));
        education.setEndYear(resultSet.getInt("end_year"));
        education.setGrade(resultSet.getString("grade"));
        education.setDescription(resultSet.getString("description"));

        return education;
    }
}

