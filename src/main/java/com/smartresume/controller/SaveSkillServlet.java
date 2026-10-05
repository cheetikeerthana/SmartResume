package com.smartresume.controller;

import com.smartresume.model.Resume;
import com.smartresume.model.Skill;
import com.smartresume.service.ResumeService;
import com.smartresume.service.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/save-skill")
public class SaveSkillServlet extends HttpServlet {

    private SkillService skillService;
    private ResumeService resumeService;

    @Override
    public void init() {
        skillService = new SkillService();
        resumeService = new ResumeService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

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

        // Verify resume ownership
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

        String skillName =
                request.getParameter("skillName");

        String skillCategory =
                request.getParameter("skillCategory");

        String proficiency =
                request.getParameter("proficiency");

        if (skillName == null
                || skillName.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&error=skill"
            );
            return;
        }

        Skill skill =
                new Skill();

        skill.setResumeId(resumeId);

        skill.setSkillName(
                skillName.trim()
        );

        skill.setSkillCategory(
                skillCategory == null
                        ? ""
                        : skillCategory.trim()
        );

        skill.setProficiency(
                proficiency == null
                        ? ""
                        : proficiency.trim()
        );

        boolean saved =
                skillService.createSkill(skill);

        if (saved) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&success=skill"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/editor.html?id="
                            + resumeId
                            + "&error=skill_save"
            );
        }
    }
}