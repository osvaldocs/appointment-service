package com.vettrack.appointment.infrastructure.adapter.jpa.repository;

import com.vettrack.appointment.infrastructure.adapter.jpa.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {
}
