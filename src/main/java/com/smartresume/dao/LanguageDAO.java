package com.smartresume.dao;

import com.smartresume.model.Language;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {

    public boolean createLanguage(Language language) {

        String sql = """
                INSERT INTO languages
                (resume_id, language, proficiency)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, language.getResumeId());
            statement.setString(2, language.getLanguage());
            statement.setString(3, language.getProficiency());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Language> getLanguagesByResumeId(int resumeId) {

        List<Language> languages = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, language, proficiency
                FROM languages
                WHERE resume_id = ?
                ORDER BY id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    languages.add(mapLanguage(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return languages;
    }

    public boolean updateLanguage(Language language) {

        String sql = """
                UPDATE languages
                SET language = ?,
                    proficiency = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, language.getLanguage());
            statement.setString(2, language.getProficiency());
            statement.setInt(3, language.getId());
            statement.setInt(4, language.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLanguage(int languageId, int resumeId) {

        String sql = """
                DELETE FROM languages
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, languageId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Language mapLanguage(ResultSet resultSet)
            throws SQLException {

        Language language = new Language();

        language.setId(resultSet.getInt("id"));
        language.setResumeId(resultSet.getInt("resume_id"));
        language.setLanguage(resultSet.getString("language"));
        language.setProficiency(resultSet.getString("proficiency"));

        return language;
    }
}
