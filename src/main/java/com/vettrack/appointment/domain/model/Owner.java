package com.vettrack.appointment.domain.model;

public class Owner {
    private Long id;
    private String fullName;
    private String identityDocument;
    private String email;
    private String phoneNumber;

    public Owner() {
    }

    public Owner(Long id, String fullName, String identityDocument, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.identityDocument = identityDocument;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
