package org.drpl.telebe.dto;

import org.drpl.telebe.model.Medicine;
import java.util.Date;
import java.util.List;

public class PrescriptionRequest {
    private Long patientId;
    private Long doctorId;
    private List<Medicine> medicines;
    private Date date;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}