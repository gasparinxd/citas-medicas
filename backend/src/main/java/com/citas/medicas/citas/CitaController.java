package com.citas.medicas.citas;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/citas")
class CitaController {

	private final CitaRepository repository;
	private final ApplicationEventPublisher events;

	CitaController(CitaRepository repository, ApplicationEventPublisher events) {
		this.repository = repository;
		this.events = events;
	}

	@GetMapping
	List<Cita> listar() {
		return repository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	Cita crear(@RequestBody Cita cita) {
		cita.setId(null);
		Cita guardada = repository.save(cita);
		events.publishEvent(new CitaAgendada(guardada.getId(), guardada.getEmailContacto(), guardada.getFechaHora()));
		return guardada;
	}
}
