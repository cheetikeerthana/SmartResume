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

@WebServlet("/create-resume")
public class CreateResumeServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);

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

        String resumeName =
                request.getParameter("resumeName");

        String templateName =
                request.getParameter("templateName");

        // Validate input
        if (resumeName == null
                || resumeName.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/create-resume.html?error=name"
            );
            return;
        }

        if (templateName == null
                || templateName.isBlank()) {

            templateName = "modern";
        }

        Resume resume = new Resume();

        resume.setUserId(userId);
        resume.setResumeName(resumeName.trim());
        resume.setTemplateName(templateName);
        resume.setSummary("");

        boolean created =
                resumeService.createResume(resume);

        if (created) {

            /*
             * ResumeDAO generates the database ID.
             * We retrieve the user's resumes and find
             * the newly created resume.
             */
            var resumes =
                    resumeService.getResumesByUserId(userId);

            Resume latestResume = null;

            for (Resume item : resumes) {

                if (latestResume == null
                        || item.getId() > latestResume.getId()) {

                    latestResume = item;
                }
            }

            if (latestResume != null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/editor.html?id="
                                + latestResume.getId()
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/dashboard.html"
                );
            }

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/create-resume.html?error=create"
            );
        }
    }
}