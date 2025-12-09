package com.vettrack.appointment.domain.dto.command;

import com.vettrack.appointment.domain.model.enums.Species;

public record RegisterPetCommand(
        String name,
        Species species,
        String race,
        int age,
        String ownerName,
        String ownerDocument
) {}
