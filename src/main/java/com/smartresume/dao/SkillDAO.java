package com.smartresume.dao;

import com.smartresume.model.Skill;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    // Add skill
    public boolean createSkill(Skill skill) {

        String sql = """
                INSERT INTO skills
                (resume_id, skill_name, skill_category, proficiency)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, skill.getResumeId());
            statement.setString(2, skill.getSkillName());
            statement.setString(3, skill.getSkillCategory());
            statement.setString(4, skill.getProficiency());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all skills for a resume
    public List<Skill> getSkillsByResumeId(int resumeId) {

        List<Skill> skills = new ArrayList<>();

        String sql = """
                SELECT id, resume_id, skill_name,
                       skill_category, proficiency
                FROM skills
                WHERE resume_id = ?
                ORDER BY id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resumeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    skills.add(mapSkill(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skills;
    }

    // Update skill
    public boolean updateSkill(Skill skill) {

        String sql = """
                UPDATE skills
                SET skill_name = ?,
                    skill_category = ?,
                    proficiency = ?
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, skill.getSkillName());
            statement.setString(2, skill.getSkillCategory());
            statement.setString(3, skill.getProficiency());
            statement.setInt(4, skill.getId());
            statement.setInt(5, skill.getResumeId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete skill
    public boolean deleteSkill(int skillId, int resumeId) {

        String sql = """
                DELETE FROM skills
                WHERE id = ?
                AND resume_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, skillId);
            statement.setInt(2, resumeId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert database row into Skill object
    private Skill mapSkill(ResultSet resultSet) throws SQLException {

        Skill skill = new Skill();

        skill.setId(resultSet.getInt("id"));
        skill.setResumeId(resultSet.getInt("resume_id"));
        skill.setSkillName(resultSet.getString("skill_name"));
        skill.setSkillCategory(resultSet.getString("skill_category"));
        skill.setProficiency(resultSet.getString("proficiency"));

        return skill;
    }
}
