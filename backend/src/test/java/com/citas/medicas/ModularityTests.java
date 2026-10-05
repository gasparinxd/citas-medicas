package com.citas.medicas;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/**
 * Verifica la estructura del monolito modular. No arranca Spring ni necesita base de datos.
 */
class ModularityTests {

	@Test
	void verificaModulos() {
		ApplicationModules.of(CitasmedicasApplication.class).verify();
	}
}
