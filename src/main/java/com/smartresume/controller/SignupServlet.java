package com.smartresume.controller;

import com.smartresume.model.User;
import com.smartresume.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword =
                request.getParameter("confirmPassword");

        if (isBlank(fullName)
                || isBlank(email)
                || isBlank(password)
                || isBlank(confirmPassword)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/signup.html?error=empty"
            );
            return;
        }

        if (!password.equals(confirmPassword)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/signup.html?error=password"
            );
            return;
        }

        if (password.length() < 8) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/signup.html?error=weak"
            );
            return;
        }

        User user = new User();

        user.setFullName(fullName.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPasswordHash(password);

        boolean registered = userService.registerUser(user);

        if (registered) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?success=registered"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/signup.html?error=exists"
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}