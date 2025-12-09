package com.vettrack.appointment.domain.dto.response;

import java.time.LocalDate;

public record PetResponse(
        Long id,
        String name,
        String species,
        String breed,
        LocalDate birthDate,
        Long ownerId
) {}
