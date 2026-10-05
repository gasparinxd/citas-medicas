package com.citas.medicas.pacientes;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")
class PacienteController {

	private final PacienteRepository repository;

	PacienteController(PacienteRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	List<Paciente> listar() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Paciente crear(@RequestBody Paciente paciente) {
		paciente.setId(null);
		return repository.save(paciente);
	}
}
