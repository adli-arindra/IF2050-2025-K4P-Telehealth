package org.drpl.telefe.domain;

import java.time.LocalDateTime;

public class MedicalRecord {
    private String diagnosis;
    private String treatment;
    private LocalDateTime recordDate;

    public MedicalRecord(String diagnosis, String treatment, LocalDateTime recordDate) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.recordDate = recordDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDateTime getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
}