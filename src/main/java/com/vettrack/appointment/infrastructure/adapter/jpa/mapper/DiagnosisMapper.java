package com.vettrack.appointment.infrastructure.adapter.jpa.mapper;

import com.vettrack.appointment.domain.model.Diagnosis;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.DiagnosisEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiagnosisMapper {
    Diagnosis toDomain(DiagnosisEntity entity);

    DiagnosisEntity toEntity(Diagnosis domain);
}
