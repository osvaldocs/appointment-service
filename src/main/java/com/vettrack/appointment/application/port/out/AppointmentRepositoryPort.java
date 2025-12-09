package com.vettrack.appointment.application.port.out;

import com.vettrack.appointment.domain.model.Appointment;
import java.util.Optional;

public interface AppointmentRepositoryPort {

    // Buscar una cita por ID
    Optional<Appointment> findById(Long id);

    // Guardar o actualizar una cita
    Appointment save(Appointment appointment);
}
