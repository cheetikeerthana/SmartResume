package com.smartresume.service;

import com.smartresume.dao.AchievementDAO;
import com.smartresume.model.Achievement;

import java.util.List;

public class AchievementService {

    private final AchievementDAO achievementDAO;

    public AchievementService() {
        this.achievementDAO = new AchievementDAO();
    }

    public boolean createAchievement(Achievement achievement) {

        if (achievement == null
                || achievement.getResumeId() <= 0) {
            return false;
        }

        if (achievement.getTitle() == null
                || achievement.getTitle().isBlank()) {
            return false;
        }

        return achievementDAO.createAchievement(achievement);
    }

    public List<Achievement> getAchievementsByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return achievementDAO.getAchievementsByResumeId(resumeId);
    }

    public boolean updateAchievement(Achievement achievement) {

        if (achievement == null
                || achievement.getId() <= 0
                || achievement.getResumeId() <= 0) {
            return false;
        }

        if (achievement.getTitle() == null
                || achievement.getTitle().isBlank()) {
            return false;
        }

        return achievementDAO.updateAchievement(achievement);
    }

    public boolean deleteAchievement(int achievementId, int resumeId) {

        if (achievementId <= 0 || resumeId <= 0) {
            return false;
        }

        return achievementDAO.deleteAchievement(
                achievementId,
                resumeId
        );
    }
}