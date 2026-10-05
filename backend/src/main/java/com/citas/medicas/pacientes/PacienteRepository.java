package com.citas.medicas.pacientes;

import org.springframework.data.jpa.repository.JpaRepository;

interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
