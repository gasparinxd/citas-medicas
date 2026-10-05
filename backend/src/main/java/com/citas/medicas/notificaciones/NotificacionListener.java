package com.citas.medicas.notificaciones;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.citas.medicas.citas.CitaAgendada;

@Component
class NotificacionListener {

	private final JavaMailSender mailSender;
	private final String remitente;

	NotificacionListener(JavaMailSender mailSender, @Value("${citas.mail.remitente}") String remitente) {
		this.mailSender = mailSender;
		this.remitente = remitente;
	}

	@ApplicationModuleListener
	void alAgendarCita(CitaAgendada evento) {
		if (evento.emailContacto() == null || evento.emailContacto().isBlank()) {
			return;
		}
		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setFrom(remitente);
		mensaje.setTo(evento.emailContacto());
		mensaje.setSubject("Cita agendada #" + evento.citaId());
		mensaje.setText("Su cita ha sido agendada para " + evento.fechaHora() + ".");
		mailSender.send(mensaje);
	}
}
