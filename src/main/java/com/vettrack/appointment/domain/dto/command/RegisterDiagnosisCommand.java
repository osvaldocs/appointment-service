package com.vettrack.appointment.domain.dto.command;

public record RegisterDiagnosisCommand (
        Long appointmentId,
        String description,
        String treatment
) {}

