package com.vettrack.appointment.application.service;

import com.vettrack.appointment.application.port.in.ConfirmAppointmentUseCase;
import com.vettrack.appointment.application.port.in.RejectAppointmentUseCase;
import com.vettrack.appointment.application.port.in.RequestAppointmentUseCase;
import com.vettrack.appointment.application.port.out.AppointmentRepositoryPort;
import com.vettrack.appointment.application.port.out.PetRepositoryPort;
import com.vettrack.appointment.application.port.out.VetAvailabilityPort;
import com.vettrack.appointment.domain.exception.DomainException;
import com.vettrack.appointment.domain.exception.PetNotFoundException;
import com.vettrack.appointment.domain.model.Appointment;
import com.vettrack.appointment.domain.model.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RequestAppointmentService implements RequestAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final PetRepositoryPort petRepository;
    private final VetAvailabilityPort vetAvailabilityPort;
    private final ConfirmAppointmentUseCase confirmAppointmentUseCase;
    private final RejectAppointmentUseCase rejectAppointmentUseCase;

    @Override
    @Transactional
    public Appointment requestAppointment(Long petId, Long veterinarianId, LocalDateTime appointmentDate) {
        // 1. Validar Mascota (Regla: debe estar ACTIVA)
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException(petId));

        if (!pet.isActive()) {
            throw new DomainException("Pet is not ACTIVE. Cannot request appointment.");
        }

        // 2. Crear cita en estado PENDING (Regla de Dominio)
        Appointment appointment = Appointment.request(petId, veterinarianId, appointmentDate);

        // 3. Guardar inicial (para generar ID)
        appointment = appointmentRepository.save(appointment);

        // 4. Verificar disponibilidad (Puerto de Salida)
        boolean isAvailable = vetAvailabilityPort.isAvailable(veterinarianId, appointmentDate);

        // 5. Orquestar flujo según disponibilidad
        if (isAvailable) {
            confirmAppointmentUseCase.confirmAppointment(appointment.getId());
            return appointmentRepository.findById(appointment.getId()).orElse(appointment);
        } else {
            rejectAppointmentUseCase.rejectAppointment(appointment.getId());
            return appointmentRepository.findById(appointment.getId()).orElse(appointment);
        }
    }
}
