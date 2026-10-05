package com.smartresume.service;

import com.smartresume.dao.LanguageDAO;
import com.smartresume.model.Language;

import java.util.List;

public class LanguageService {

    private final LanguageDAO languageDAO;

    public LanguageService() {
        this.languageDAO = new LanguageDAO();
    }

    public boolean createLanguage(Language language) {

        if (language == null
                || language.getResumeId() <= 0) {
            return false;
        }

        if (language.getLanguage() == null
                || language.getLanguage().isBlank()) {
            return false;
        }

        return languageDAO.createLanguage(language);
    }

    public List<Language> getLanguagesByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return languageDAO.getLanguagesByResumeId(resumeId);
    }

    public boolean updateLanguage(Language language) {

        if (language == null
                || language.getId() <= 0
                || language.getResumeId() <= 0) {
            return false;
        }

        if (language.getLanguage() == null
                || language.getLanguage().isBlank()) {
            return false;
        }

        return languageDAO.updateLanguage(language);
    }

    public boolean deleteLanguage(int languageId, int resumeId) {

        if (languageId <= 0 || resumeId <= 0) {
            return false;
        }

        return languageDAO.deleteLanguage(
                languageId,
                resumeId
        );
    }
}