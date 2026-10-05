package com.smartresume.controller;

import com.smartresume.model.User;
import com.smartresume.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (isBlank(email) || isBlank(password)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?error=empty"
            );
            return;
        }

        User user = userService.loginUser(
                email.trim().toLowerCase(),
                password
        );

        if (user == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?error=invalid"
            );
            return;
        }

        HttpSession session = request.getSession(true);

        session.setAttribute("user", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getFullName());
        session.setAttribute("userEmail", user.getEmail());

        session.setMaxInactiveInterval(30 * 60);

        response.sendRedirect(
                request.getContextPath()
                        + "/dashboard.html"
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}