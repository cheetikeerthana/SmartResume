package com.smartresume.dao;

import com.smartresume.model.User;
import com.smartresume.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Create a new user
    public boolean createUser(User user) {

        String sql = """
                INSERT INTO users
                (full_name, email, password_hash, phone, location,
                 linkedin_url, github_url, portfolio_url)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getLocation());
            statement.setString(6, user.getLinkedinUrl());
            statement.setString(7, user.getGithubUrl());
            statement.setString(8, user.getPortfolioUrl());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
    System.err.println("ERROR in createUser(): " + e.getMessage());
    e.printStackTrace();
    return false;
}
    }

    // Find a user by email
    public User getUserByEmail(String email) {

        String sql = """
                SELECT id, full_name, email, password_hash,
                       phone, location, linkedin_url,
                       github_url, portfolio_url
                FROM users
                WHERE email = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User();

                    user.setId(resultSet.getInt("id"));
                    user.setFullName(resultSet.getString("full_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(resultSet.getString("password_hash"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setLocation(resultSet.getString("location"));
                    user.setLinkedinUrl(resultSet.getString("linkedin_url"));
                    user.setGithubUrl(resultSet.getString("github_url"));
                    user.setPortfolioUrl(resultSet.getString("portfolio_url"));

                    return user;
                }
            }

        } catch (SQLException e) {
    System.err.println("ERROR in getUserByEmail(): " + e.getMessage());
    e.printStackTrace();
}

        return null;
    }
}
