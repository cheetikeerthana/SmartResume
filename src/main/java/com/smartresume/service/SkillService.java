package com.smartresume.service;

import com.smartresume.dao.SkillDAO;
import com.smartresume.model.Skill;

import java.util.List;

public class SkillService {

    private final SkillDAO skillDAO;

    public SkillService() {
        this.skillDAO = new SkillDAO();
    }

    public boolean createSkill(Skill skill) {

        if (skill == null || skill.getResumeId() <= 0) {
            return false;
        }

        if (skill.getSkillName() == null
                || skill.getSkillName().isBlank()) {
            return false;
        }

        return skillDAO.createSkill(skill);
    }

    public List<Skill> getSkillsByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return skillDAO.getSkillsByResumeId(resumeId);
    }

    public boolean updateSkill(Skill skill) {

        if (skill == null
                || skill.getId() <= 0
                || skill.getResumeId() <= 0) {
            return false;
        }

        if (skill.getSkillName() == null
                || skill.getSkillName().isBlank()) {
            return false;
        }

        return skillDAO.updateSkill(skill);
    }

    public boolean deleteSkill(int skillId, int resumeId) {

        if (skillId <= 0 || resumeId <= 0) {
            return false;
        }

        return skillDAO.deleteSkill(skillId, resumeId);
    }
}