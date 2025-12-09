package com.vettrack.appointment.infrastructure.adapter.rest;

import com.vettrack.appointment.application.port.out.VetAvailabilityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VetAvailabilityRestAdapter implements VetAvailabilityPort {

    private final RestTemplate restTemplate;
    // URL del mock service (asumiendo que corre en localhost:8081 según
    // docker-compose)
    private final String MOCK_SERVICE_URL = "http://localhost:8081/availability";

    @Override
    public boolean isAvailable(Long veterinarianId, LocalDateTime dateTime) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("veterinarioId", veterinarianId);
            request.put("fecha", dateTime.toLocalDate().toString());
            request.put("hora", dateTime.toLocalTime().toString());

            // Hacemos POST al mock service
            // La respuesta esperada es: { "disponible": true, ... }
            Map<String, Object> response = restTemplate.postForObject(MOCK_SERVICE_URL, request, Map.class);

            if (response != null && response.containsKey("disponible")) {
                return (Boolean) response.get("disponible");
            }
            return false; // Por defecto si falla o no hay respuesta clara
        } catch (Exception e) {
            // En caso de error de conexión, asumimos no disponible o lanzamos excepción
            // Para el examen, loguear y retornar false es seguro.
            System.err.println("Error calling Vet Mock Service: " + e.getMessage());
            return false;
        }
    }
}
