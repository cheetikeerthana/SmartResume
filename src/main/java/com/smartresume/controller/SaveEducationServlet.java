package com.smartresume.controller;

import com.smartresume.model.Education;
import com.smartresume.model.Resume;
import com.smartresume.service.EducationService;
import com.smartresume.service.ResumeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/save-education")
public class SaveEducationServlet extends HttpServlet {

    private EducationService educationService;
    private ResumeService resumeService;

    @Override
    public void init() {
        educationService = new EducationService();
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

        /*
         * Verify that this resume belongs
         * to the currently logged-in user.
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

        String institution =
                request.getParameter("institution");

        String degree =
                request.getParameter("degree");

        String fieldOfStudy =
                request.getParameter("fieldOfStudy");

        String startYearParameter =
                request.getParameter("startYear");

        String endYearParameter =
                request.getParameter("endYear");

        String grade =
                request.getParameter("grade");

        String description =
                request.getParameter("description");

        // Basic validation
        if (institution == null
                || institution.isBlank()
                || degree == null
                || degree.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&error=education"
            );
            return;
        }

        int startYear = 0;
        int endYear = 0;

        try {

            if (startYearParameter != null
                    && !startYearParameter.isBlank()) {

                startYear =
                        Integer.parseInt(
                                startYearParameter
                        );
            }

            if (endYearParameter != null
                    && !endYearParameter.isBlank()) {

                endYear =
                        Integer.parseInt(
                                endYearParameter
                        );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&error=education_year"
            );
            return;
        }

        Education education =
                new Education();

        education.setResumeId(resumeId);

        education.setInstitution(
                institution.trim()
        );

        education.setDegree(
                degree.trim()
        );

        education.setFieldOfStudy(
                fieldOfStudy == null
                        ? ""
                        : fieldOfStudy.trim()
        );

        education.setStartYear(startYear);
        education.setEndYear(endYear);

        education.setGrade(
                grade == null
                        ? ""
                        : grade.trim()
        );

        education.setDescription(
                description == null
                        ? ""
                        : description.trim()
        );

        boolean saved =
                educationService.createEducation(
                        education
                );

        if (saved) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&success=education"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&error=education_save"
            );
        }
    }
}