package com.smartresume.service;

import com.smartresume.dao.CertificationDAO;
import com.smartresume.model.Certification;

import java.util.List;

public class CertificationService {

    private final CertificationDAO certificationDAO;

    public CertificationService() {
        this.certificationDAO = new CertificationDAO();
    }

    public boolean createCertification(Certification certification) {

        if (certification == null
                || certification.getResumeId() <= 0) {
            return false;
        }

        if (certification.getName() == null
                || certification.getName().isBlank()) {
            return false;
        }

        return certificationDAO.createCertification(certification);
    }

    public List<Certification> getCertificationsByResumeId(int resumeId) {

        if (resumeId <= 0) {
            return List.of();
        }

        return certificationDAO.getCertificationsByResumeId(resumeId);
    }

    public boolean updateCertification(Certification certification) {

        if (certification == null
                || certification.getId() <= 0
                || certification.getResumeId() <= 0) {
            return false;
        }

        if (certification.getName() == null
                || certification.getName().isBlank()) {
            return false;
        }

        return certificationDAO.updateCertification(certification);
    }

    public boolean deleteCertification(int certificationId, int resumeId) {

        if (certificationId <= 0 || resumeId <= 0) {
            return false;
        }

        return certificationDAO.deleteCertification(
                certificationId,
                resumeId
        );
    }
}