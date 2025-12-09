package com.vettrack.appointment.application.service;

import com.vettrack.appointment.application.port.in.RejectAppointmentUseCase;
import com.vettrack.appointment.application.port.out.AppointmentRepositoryPort;
import com.vettrack.appointment.domain.exception.AppointmentNotFoundException;
import com.vettrack.appointment.domain.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RejectAppointmentService implements RejectAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;

    @Override
    @Transactional
    public void rejectAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        appointment.reject();

        appointmentRepository.save(appointment);
    }

}
