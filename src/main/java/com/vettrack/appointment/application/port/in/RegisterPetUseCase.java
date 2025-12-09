package com.vettrack.appointment.application.port.in;

import com.vettrack.appointment.domain.model.Pet;
import com.vettrack.appointment.domain.model.enums.Species;

public interface RegisterPetUseCase {
    Pet registerPet(String name, Species species, String race, int age, String ownerName, String ownerDocument);
}

