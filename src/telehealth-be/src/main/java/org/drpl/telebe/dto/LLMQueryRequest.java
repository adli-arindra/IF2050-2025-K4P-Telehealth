package org.drpl.telebe.dto;

public class LLMQueryRequest {
    private String query;
    private Long patientId;

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
}
