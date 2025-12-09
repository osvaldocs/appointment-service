package com.vettrack.appointment.domain.model;

import com.vettrack.appointment.domain.model.enums.PetStatus;
import com.vettrack.appointment.domain.model.enums.Species;
import com.vettrack.appointment.domain.exception.DomainException;

public class Pet {
    private Long id;
    private String name;
    private Species species;
    private String race;
    private int age;
    private PetStatus petStatus;
    private String ownerName;
    private String ownerDocument;

    public Pet() {
    }

    public Pet(Long id, String name, Species species, String race, int age, PetStatus petStatus, String ownerName,
            String ownerDocument) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age;
        this.petStatus = petStatus;
        this.ownerName = ownerName;
        this.ownerDocument = ownerDocument;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PetStatus getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(PetStatus petStatus) {
        this.petStatus = petStatus;
    }

    public boolean isActive() {
        return this.petStatus == PetStatus.ACTIVE;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerDocument() {
        return ownerDocument;
    }

    public void setOwnerDocument(String ownerDocument) {
        this.ownerDocument = ownerDocument;
    }

    public static Pet create(String name, Species species, String race, int age, String ownerName,
            String ownerDocument) {
        if (age <= 0) {
            throw new DomainException("Age must be greater than 0.");
        }
        if (ownerDocument == null || ownerDocument.trim().isEmpty()) {
            throw new DomainException("Owner document is required.");
        }

        Pet pet = new Pet();
        pet.setName(name);
        pet.setSpecies(species);
        pet.setRace(race);
        pet.setAge(age);
        pet.setOwnerName(ownerName);
        pet.setOwnerDocument(ownerDocument);
        pet.setPetStatus(PetStatus.ACTIVE);
        return pet;
    }
}
