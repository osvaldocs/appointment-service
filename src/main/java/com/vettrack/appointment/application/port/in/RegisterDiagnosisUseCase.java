package com.vettrack.appointment.application.port.in;

import com.vettrack.appointment.domain.model.Diagnosis;

public interface RegisterDiagnosisUseCase {
    Diagnosis registerDiagnosis(Long appointmentId, String description, String treatment);
}
