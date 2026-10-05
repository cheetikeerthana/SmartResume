package com.smartresume.controller;

import com.smartresume.model.Resume;
import com.smartresume.service.ResumeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editor")
public class EditorServlet extends HttpServlet {

    private ResumeService resumeService;

    @Override
    public void init() {
        resumeService = new ResumeService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // User must be logged in
        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.html?error=login_required"
            );
            return;
        }

        int userId =
                (Integer) session.getAttribute("userId");

        String idParameter =
                request.getParameter("id");

        // Resume ID is required
        if (idParameter == null
                || idParameter.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard.html?error=resume"
            );
            return;
        }

        int resumeId;

        try {

            resumeId =
                    Integer.parseInt(idParameter);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard.html?error=resume"
            );
            return;
        }

        // Get resume only if it belongs to this user
        Resume resume =
                resumeService.getResumeById(
                        resumeId,
                        userId
                );

        if (resume == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard.html?error=resume"
            );
            return;
        }

        // Store resume information for the editor
        request.setAttribute("resume", resume);

        // Forward to editor page
        request.getRequestDispatcher(
                "/editor.html"
        ).forward(request, response);
    }
}