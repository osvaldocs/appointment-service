package com.vettrack.appointment.domain.dto.response;

public record VeterinarianResponse(
        Long id,
        String firstName,
        String lastName,
        String licenseNumber,
        String email
) {}
