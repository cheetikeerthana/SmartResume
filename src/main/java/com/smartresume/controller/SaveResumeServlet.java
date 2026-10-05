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

@WebServlet("/save-resume")
public class SaveResumeServlet extends HttpServlet {

    private ResumeService resumeService;

    @Override
    public void init() {
        resumeService = new ResumeService();
    }

    @Override
    protected void doPost(
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

        String resumeIdParameter =
                request.getParameter("resumeId");

        String resumeName =
                request.getParameter("resumeName");

        String templateName =
                request.getParameter("templateName");

        String summary =
                request.getParameter("summary");

        // Validate resume ID
        if (resumeIdParameter == null
                || resumeIdParameter.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard.html?error=resume"
            );
            return;
        }

        int resumeId;

        try {

            resumeId =
                    Integer.parseInt(resumeIdParameter);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/dashboard.html?error=resume"
            );
            return;
        }

        // Validate resume name
        if (resumeName == null
                || resumeName.isBlank()) {

            resumeName = "My Resume";
        }

        if (templateName == null
                || templateName.isBlank()) {

            templateName = "modern";
        }

        if (summary == null) {
            summary = "";
        }

        /*
         * Get the existing resume using both
         * resume ID and logged-in user ID.
         *
         * This prevents one user from editing
         * another user's resume.
         */
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

        // Update resume information
        resume.setResumeName(
                resumeName.trim()
        );

        resume.setTemplateName(
                templateName.trim()
        );

        resume.setSummary(
                summary.trim()
        );

        boolean updated =
                resumeService.updateResume(
                        resume,
                        userId
                );

        if (updated) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&success=saved"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&error=save"
            );
        }
    }
}