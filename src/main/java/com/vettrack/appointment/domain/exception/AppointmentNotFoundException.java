package com.vettrack.appointment.domain.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
        super("Appointment with id " + id + " not found.");
    }
}
