package com.vettrack.appointment.infrastructure.adapter.jpa;

import com.vettrack.appointment.application.port.out.PetRepositoryPort;
import com.vettrack.appointment.domain.model.Pet;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.PetEntity;
import com.vettrack.appointment.infrastructure.adapter.jpa.mapper.PetMapper;
import com.vettrack.appointment.infrastructure.adapter.jpa.repository.PetJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PetJpaAdapter implements PetRepositoryPort {

    private final PetJpaRepository petJpaRepository;
    private final PetMapper petMapper;

    @Override
    public Pet save(Pet pet) {
        PetEntity entity = petMapper.toEntity(pet);
        PetEntity savedEntity = petJpaRepository.save(entity);
        return petMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petJpaRepository.findById(id)
                .map(petMapper::toDomain);
    }
}
