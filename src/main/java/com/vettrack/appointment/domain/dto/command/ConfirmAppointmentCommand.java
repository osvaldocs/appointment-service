package com.vettrack.appointment.domain.dto.command;

public record ConfirmAppointmentCommand(
        Long appointmentId,
        boolean approved
) {}
