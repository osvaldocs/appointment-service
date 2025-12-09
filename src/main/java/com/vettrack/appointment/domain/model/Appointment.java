package com.vettrack.appointment.domain.model;

import com.vettrack.appointment.domain.model.enums.AppointmentStatus;
import com.vettrack.appointment.domain.exception.DomainException;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private Long petId;
    private Long veterinarianId;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private Diagnosis diagnosis;

    public Appointment() {
    }

    public Appointment(Long id, Long petId, Long veterinarianId, LocalDateTime appointmentDate,
            AppointmentStatus status, Diagnosis diagnosis) {
        this.id = id;
        this.petId = petId;
        this.veterinarianId = veterinarianId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.diagnosis = diagnosis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(Long veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void confirm() {
        if (this.status != AppointmentStatus.PENDING) {
            throw new DomainException("Only PENDING appointments can be confirmed.");
        }
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void reject() {
        if (this.status != AppointmentStatus.PENDING) {
            throw new DomainException("Only PENDING appointments can be rejected.");
        }
        this.status = AppointmentStatus.REJECTED;
    }

    public void registerDiagnosis(Diagnosis diagnosis) {
        if (this.status != AppointmentStatus.CONFIRMED) {
            throw new DomainException("Diagnosis can only be registered for CONFIRMED appointments.");
        }
        this.diagnosis = diagnosis;
    }

    public static Appointment request(Long petId, Long veterinarianId, LocalDateTime appointmentDate) {
        if (appointmentDate == null) {
            throw new DomainException("Appointment date is required.");
        }
        if (appointmentDate.isBefore(LocalDateTime.now())) {
            throw new DomainException("Appointment date must be in the future.");
        }
        Appointment appointment = new Appointment();
        appointment.setPetId(petId);
        appointment.setVeterinarianId(veterinarianId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointment;
    }
}
