package com.citas.medicas.citas;

import java.time.LocalDateTime;

/**
 * Evento publicado por el módulo de citas cuando se agenda una cita.
 */
public record CitaAgendada(Long citaId, String emailContacto, LocalDateTime fechaHora) {
}
