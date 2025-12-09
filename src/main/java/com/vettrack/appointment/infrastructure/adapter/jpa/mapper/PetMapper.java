package com.vettrack.appointment.infrastructure.adapter.jpa.mapper;

import com.vettrack.appointment.domain.model.Pet;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.PetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {
    Pet toDomain(PetEntity entity);

    PetEntity toEntity(Pet domain);
}
