package com.vettrack.appointment.domain.dto.response;

import com.vettrack.appointment.domain.model.enums.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long appointmentId,
        Long petId,
        Long veterinarianId,
        LocalDateTime appointmentDate,
        AppointmentStatus status
) {}
