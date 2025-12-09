package com.vettrack.availability;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@RestController
public class AvailabilityController {

    @PostMapping("/availability")
    public AvailabilityResponse checkAvailability(@RequestBody AvailabilityRequest request) {
        // Lógica sugerida en el PDF:
        // 1. Generar un hash combinando los tres valores.
        // 2. Si el hash % 2 == 0 → disponible.
        // 3. Si el hash % 2 != 0 → no disponible.

        int hash = Objects.hash(request.getVeterinarioId(), request.getFecha(), request.getHora());
        boolean disponible = (hash % 2 == 0);

        AvailabilityResponse response = new AvailabilityResponse();
        response.setVeterinarioId(request.getVeterinarioId());
        response.setDisponible(disponible);
        response.setMotivo(disponible ? "Horario libre" : "Agenda ocupada");

        return response;
    }

    @Data
    public static class AvailabilityRequest {
        private Long veterinarioId;
        private LocalDate fecha;
        private LocalTime hora;
    }

    @Data
    public static class AvailabilityResponse {
        private Long veterinarioId;
        private boolean disponible;
        private String motivo;
    }
}
