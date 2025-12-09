package com.vettrack.appointment.infrastructure.adapter.jpa.repository;

import com.vettrack.appointment.infrastructure.adapter.jpa.entity.DiagnosisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisJpaRepository extends JpaRepository<DiagnosisEntity, Long> {
}
