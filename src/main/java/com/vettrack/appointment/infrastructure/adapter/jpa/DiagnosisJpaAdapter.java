package com.vettrack.appointment.infrastructure.adapter.jpa;

import com.vettrack.appointment.application.port.out.DiagnosisRepositoryPort;
import com.vettrack.appointment.domain.model.Diagnosis;
import com.vettrack.appointment.infrastructure.adapter.jpa.entity.DiagnosisEntity;
import com.vettrack.appointment.infrastructure.adapter.jpa.mapper.DiagnosisMapper;
import com.vettrack.appointment.infrastructure.adapter.jpa.repository.DiagnosisJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiagnosisJpaAdapter implements DiagnosisRepositoryPort {

    private final DiagnosisJpaRepository diagnosisJpaRepository;
    private final DiagnosisMapper diagnosisMapper;

    @Override
    public Diagnosis save(Diagnosis diagnosis) {
        DiagnosisEntity entity = diagnosisMapper.toEntity(diagnosis);
        DiagnosisEntity savedEntity = diagnosisJpaRepository.save(entity);
        return diagnosisMapper.toDomain(savedEntity);
    }

    @Override
    public java.util.Optional<Diagnosis> findById(Long id) {
        return diagnosisJpaRepository.findById(id)
                .map(diagnosisMapper::toDomain);
    }
}
