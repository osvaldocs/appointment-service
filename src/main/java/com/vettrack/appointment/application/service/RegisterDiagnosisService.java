package com.vettrack.appointment.application.service;

import com.vettrack.appointment.application.port.in.RegisterDiagnosisUseCase;
import com.vettrack.appointment.application.port.out.AppointmentRepositoryPort;
import com.vettrack.appointment.application.port.out.DiagnosisRepositoryPort;
import com.vettrack.appointment.domain.exception.AppointmentNotFoundException;
import com.vettrack.appointment.domain.model.Appointment;
import com.vettrack.appointment.domain.model.Diagnosis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegisterDiagnosisService implements RegisterDiagnosisUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final DiagnosisRepositoryPort diagnosisRepository;

    @Override
    @Transactional
    public Diagnosis registerDiagnosis(Long appointmentId, String description, String treatment) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        Diagnosis diagnosis = Diagnosis.create(appointmentId, description, treatment);

        // Validates state (must be CONFIRMED) and links diagnosis
        appointment.registerDiagnosis(diagnosis);

        // Save diagnosis first (if needed by DB constraints) or cascade.
        // Assuming we need to save diagnosis explicitly or cascade.
        // The original code saved diagnosis then appointment.
        Diagnosis savedDiagnosis = diagnosisRepository.save(diagnosis);

        // Update appointment with the saved diagnosis (if ID generated)
        appointment.setDiagnosis(savedDiagnosis);
        appointmentRepository.save(appointment);

        return savedDiagnosis;
    }
}
