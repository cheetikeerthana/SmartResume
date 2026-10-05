package com.smartresume.service;

import com.smartresume.dao.UserDAO;
import com.smartresume.model.User;
import com.smartresume.util.PasswordUtil;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {

        if (user == null) {
            return false;
        }

        if (user.getFullName() == null
                || user.getFullName().isBlank()) {
            return false;
        }

        if (user.getEmail() == null
                || user.getEmail().isBlank()) {
            return false;
        }

        if (user.getPasswordHash() == null
                || user.getPasswordHash().isBlank()) {
            return false;
        }

        User existingUser =
                userDAO.getUserByEmail(user.getEmail());

        if (existingUser != null) {
            return false;
        }

        String hashedPassword =
                PasswordUtil.hashPassword(
                        user.getPasswordHash()
                );

        user.setPasswordHash(hashedPassword);

        return userDAO.createUser(user);
    }

    public User loginUser(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            return null;
        }

        boolean passwordMatches =
                PasswordUtil.verifyPassword(
                        password,
                        user.getPasswordHash()
                );

        if (passwordMatches) {
            return user;
        }

        return null;
    }

    public User getUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            return null;
        }

        return userDAO.getUserByEmail(email);
    }
}