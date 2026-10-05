package com.smartresume.dao;

import com.smartresume.model.Achievement;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AchievementDAO {

    // Add achievement
    public boolean createAchievement(Achievement achievement) {

        String sql = """
                INSERT INTO achievements
                (resume_id, title, description, date)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, achievement.getResumeId());
            statement.setString(2, achievement.getTitle());
            statement.setString(3, achievement.getDescription());

            if (achievement.getDate() != null) {
                statement.setDate(4, Date.valueOf(achievement.getDate()));
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get achievements for a resume
    public List<Achievement> getAchievementsByResumeId(int resumeId) {

        List<Achievement> achievements = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, title, description, date
                FROM achievements
                WHERE resume_id = ?
                ORDER BY date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    achievements.add(mapAchievement(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return achievements;
    }

    // Update achievement
    public boolean updateAchievement(Achievement achievement) {

        String sql = """
                UPDATE achievements
                SET title = ?,
                    description = ?,
                    date = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, achievement.getTitle());
            statement.setString(2, achievement.getDescription());

            if (achievement.getDate() != null) {
                statement.setDate(3, Date.valueOf(achievement.getDate()));
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }

            statement.setInt(4, achievement.getId());
            statement.setInt(5, achievement.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete achievement
    public boolean deleteAchievement(int achievementId, int resumeId) {

        String sql = """
                DELETE FROM achievements
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, achievementId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert database row into Achievement object
    private Achievement mapAchievement(ResultSet resultSet)
            throws SQLException {

        Achievement achievement = new Achievement();

        achievement.setId(resultSet.getInt("id"));
        achievement.setResumeId(resultSet.getInt("resume_id"));
        achievement.setTitle(resultSet.getString("title"));
        achievement.setDescription(
                resultSet.getString("description")
        );

        Date date = resultSet.getDate("date");

        if (date != null) {
            achievement.setDate(date.toLocalDate());
        }

        return achievement;
    }
}