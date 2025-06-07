package org.drpl.telefe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class PrescriptionResponse {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private List<Medicine> medicines;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSS]")
    private LocalDateTime date;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public List<Medicine> getMedicines() { return medicines; }
    public void setMedicines(List<Medicine> medicines) { this.medicines = medicines; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    @Override
    public String toString() {
        return "PrescriptionResponse{" + "id=" + id + ", patientId=" + patientId + ", doctorId=" + doctorId + ", date=" + date + ", medicinesCount=" + (medicines != null ? medicines.size() : 0) + '}';
    }
}