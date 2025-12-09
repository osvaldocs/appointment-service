package com.vettrack.appointment.infrastructure.adapter.jpa.repository;

import com.vettrack.appointment.infrastructure.adapter.jpa.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
}
