package com.vettrack.appointment.domain.dto.command;

public record RequestAppointmentCommand(
        Long petId,
        Long veterinarianId,
        String reason
) {}
