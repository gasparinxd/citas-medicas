package com.citas.medicas.medicos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicos")
class MedicoController {

	private final MedicoRepository repository;

	MedicoController(MedicoRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	List<Medico> listar() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Medico crear(@RequestBody Medico medico) {
		medico.setId(null);
		return repository.save(medico);
	}
}
