package com.vettrack.appointment.application.port.in;

import com.vettrack.appointment.domain.model.Appointment;

import java.time.LocalDateTime;

public interface RequestAppointmentUseCase {
    Appointment requestAppointment(Long petId, Long veterinarianId, LocalDateTime appointmentDate);
}
