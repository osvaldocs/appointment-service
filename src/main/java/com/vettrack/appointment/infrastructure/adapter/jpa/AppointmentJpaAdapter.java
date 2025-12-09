package com.vettrack.appointment.infrastructure.adapter.jpa;

import com.vettrack.appointment.application.port.out.AppointmentRepositoryPort;
import com.vettrack.appointment.domain.model.Appointment;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.AppointmentEntity;
import com.vettrack.appointment.infrastructure.adapter.jpa.mapper.AppointmentMapper;
import com.vettrack.appointment.infrastructure.adapter.jpa.repository.AppointmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppointmentJpaAdapter implements AppointmentRepositoryPort {

    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentJpaRepository.findById(id)
                .map(appointmentMapper::toDomain);
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = appointmentMapper.toEntity(appointment);
        AppointmentEntity savedEntity = appointmentJpaRepository.save(entity);
        return appointmentMapper.toDomain(savedEntity);
    }
}
