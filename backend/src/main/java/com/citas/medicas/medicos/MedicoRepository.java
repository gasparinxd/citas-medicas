package com.citas.medicas.medicos;

import org.springframework.data.jpa.repository.JpaRepository;

interface MedicoRepository extends JpaRepository<Medico, Long> {
}
