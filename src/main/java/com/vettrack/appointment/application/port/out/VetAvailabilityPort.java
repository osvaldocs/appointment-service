package com.vettrack.appointment.application.port.out;

import java.time.LocalDateTime;

public interface VetAvailabilityPort {
    boolean isAvailable(Long veterinarianId, LocalDateTime dateTime);
}
