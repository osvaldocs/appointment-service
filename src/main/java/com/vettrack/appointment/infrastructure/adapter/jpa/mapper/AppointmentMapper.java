package com.vettrack.appointment.infrastructure.adapter.jpa.mapper;

import com.vettrack.appointment.domain.model.Appointment;
import com.vettrack.appointment.domain.model.Diagnosis;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.AppointmentEntity;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.DiagnosisEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "diagnosis", source = "diagnosis")
    Appointment toDomain(AppointmentEntity entity);

    @Mapping(target = "diagnosis", source = "diagnosis")
    AppointmentEntity toEntity(Appointment domain);

    Diagnosis toDomain(DiagnosisEntity entity);

    DiagnosisEntity toEntity(Diagnosis domain);
}
