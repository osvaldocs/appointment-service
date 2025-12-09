package com.vettrack.appointment.domain.dto.response;

public record DiagnosisResponse(
        Long id,
        Long appointmentId,
        String description,
        String treatment
) {}
