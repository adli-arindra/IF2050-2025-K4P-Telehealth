package org.drpl.telefe.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class MedicalRecord {
    private String diagnosis;
    private String treatment;
    private Date recordDate;

    public MedicalRecord() {};

    public MedicalRecord(String diagnosis, String treatment, Date recordDate) {
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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}