package com.vettrack.appointment.domain.model;

import java.time.LocalDate;
import com.vettrack.appointment.domain.exception.DomainException;

public class Diagnosis {
    private Long id;
    private Long appointmentId; // ← agregamos esto
    private String description;
    private String treatment;
    private LocalDate date;

    public Diagnosis() {
    }

    public Diagnosis(Long id, Long appointmentId, String description, String treatment, LocalDate date) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.description = description;
        this.treatment = treatment;
        this.date = date;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static Diagnosis create(Long appointmentId, String description, String treatment) {
        if (description == null || description.trim().isEmpty()) {
            throw new DomainException("Diagnosis description is required.");
        }
        if (treatment == null || treatment.trim().isEmpty()) {
            throw new DomainException("Treatment suggestion is required.");
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setAppointmentId(appointmentId);
        diagnosis.setDescription(description);
        diagnosis.setTreatment(treatment);
        diagnosis.setDate(LocalDate.now());
        return diagnosis;
    }
}
