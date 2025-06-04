package org.drpl.telebe.dto;

import org.drpl.telebe.model.Medicine;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class PrescriptionResponse {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private List<Medicine> medicines;
    private LocalDateTime date;

    public PrescriptionResponse(Long id, Long patientId, Long doctorId, List<Medicine> medicines, LocalDateTime date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medicines = medicines;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}