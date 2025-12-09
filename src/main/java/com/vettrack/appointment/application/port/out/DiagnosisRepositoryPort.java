package com.vettrack.appointment.application.port.out;

import com.vettrack.appointment.domain.model.Diagnosis;
import java.util.Optional;

public interface DiagnosisRepositoryPort {
    Diagnosis save(Diagnosis diagnosis);
    Optional<Diagnosis> findById(Long id);
}
