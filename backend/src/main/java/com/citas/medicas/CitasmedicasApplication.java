package com.citas.medicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableAsync;

@Modulithic
@EnableAsync
@SpringBootApplication
public class CitasmedicasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitasmedicasApplication.class, args);
	}

}
