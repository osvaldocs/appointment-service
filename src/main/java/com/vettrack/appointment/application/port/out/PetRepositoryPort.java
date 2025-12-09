package com.vettrack.appointment.application.port.out;

import com.vettrack.appointment.domain.model.Pet;

public interface PetRepositoryPort {
    Pet save(Pet pet);

    java.util.Optional<Pet> findById(Long id);
}
