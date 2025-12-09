package com.vettrack.appointment.application.service;

import com.vettrack.appointment.application.port.in.RegisterPetUseCase;
import com.vettrack.appointment.application.port.out.PetRepositoryPort;
import com.vettrack.appointment.domain.model.Pet;
import com.vettrack.appointment.domain.model.enums.Species;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegisterPetService implements RegisterPetUseCase {

    private final PetRepositoryPort petRepository;

    @Override
    @Transactional
    public Pet registerPet(String name, Species species, String race, int age, String ownerName, String ownerDocument) {
        Pet pet = Pet.create(name, species, race, age, ownerName, ownerDocument);
        return petRepository.save(pet);
    }
}
