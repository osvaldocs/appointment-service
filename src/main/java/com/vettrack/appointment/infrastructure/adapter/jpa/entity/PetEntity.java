package com.vettrack.appointment.infrastructure.adapter.jpa.entity;

import com.vettrack.appointment.domain.model.enums.PetStatus;
import com.vettrack.appointment.domain.model.enums.Species;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String race;
    private int age;

    @Enumerated(EnumType.STRING)
    private PetStatus petStatus;

    private String ownerName;
    private String ownerDocument;
}
