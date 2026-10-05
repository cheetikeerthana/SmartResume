package com.smartresume.dao;

import com.smartresume.model.Certification;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CertificationDAO {

    // Add certification
    public boolean createCertification(Certification certification) {

        String sql = """
                INSERT INTO certifications
                (resume_id, name, issuing_organization, issue_date,
                 credential_url, description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, certification.getResumeId());
            statement.setString(2, certification.getName());
            statement.setString(3, certification.getIssuingOrganization());

            if (certification.getIssueDate() != null) {
                statement.setDate(4, Date.valueOf(certification.getIssueDate()));
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }

            statement.setString(5, certification.getCredentialUrl());
            statement.setString(6, certification.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get certifications for a resume
    public List<Certification> getCertificationsByResumeId(int resumeId) {

        List<Certification> certifications = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, name, issuing_organization,
                       issue_date, credential_url, description
                FROM certifications
                WHERE resume_id = ?
                ORDER BY issue_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    certifications.add(mapCertification(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certifications;
    }

    // Update certification
    public boolean updateCertification(Certification certification) {

        String sql = """
                UPDATE certifications
                SET name = ?,
                    issuing_organization = ?,
                    issue_date = ?,
                    credential_url = ?,
                    description = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, certification.getName());
            statement.setString(2, certification.getIssuingOrganization());

            if (certification.getIssueDate() != null) {
                statement.setDate(3, Date.valueOf(certification.getIssueDate()));
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }

            statement.setString(4, certification.getCredentialUrl());
            statement.setString(5, certification.getDescription());
            statement.setInt(6, certification.getId());
            statement.setInt(7, certification.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete certification
    public boolean deleteCertification(int certificationId, int resumeId) {

        String sql = """
                DELETE FROM certifications
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, certificationId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert database row into Certification object
    private Certification mapCertification(ResultSet resultSet)
            throws SQLException {

        Certification certification = new Certification();

        certification.setId(resultSet.getInt("id"));
        certification.setResumeId(resultSet.getInt("resume_id"));
        certification.setName(resultSet.getString("name"));
        certification.setIssuingOrganization(
                resultSet.getString("issuing_organization")
        );

        Date issueDate = resultSet.getDate("issue_date");

        if (issueDate != null) {
            certification.setIssueDate(issueDate.toLocalDate());
        }

        certification.setCredentialUrl(
                resultSet.getString("credential_url")
        );

        certification.setDescription(
                resultSet.getString("description")
        );

        return certification;
    }
}
