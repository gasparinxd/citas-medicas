# Citas Médicas

[![docker-compose](https://github.com/gasparinxd/citas-medicas/actions/workflows/docker-compose.yml/badge.svg)](https://github.com/gasparinxd/citas-medicas/actions/workflows/docker-compose.yml)
[![devcontainer](https://github.com/gasparinxd/citas-medicas/actions/workflows/devcontainer.yml/badge.svg)](https://github.com/gasparinxd/citas-medicas/actions/workflows/devcontainer.yml)

Sistema base para la gestión de citas médicas.

- **backend/**: monolito modular con Spring Boot 4 (Java 21), Spring Modulith, PostgreSQL y REST.
- **frontend/**: aplicación web en React (Vite), servida con Nginx en Docker.
- **docker-compose.yml**: levanta PostgreSQL, Mailpit (mock de correos), backend y frontend a la vez.

## Módulos del backend

Cada módulo es un paquete bajo `com.citas.medicas`; Spring Modulith verifica sus dependencias en `ModularityTests`.

| Módulo           | Responsabilidad                                                    |
|------------------|--------------------------------------------------------------------|
| `pacientes`      | Registro de pacientes                                              |
| `medicos`        | Registro de médicos                                                |
| `citas`          | Agenda de citas; publica el evento `CitaAgendada`                  |
| `notificaciones` | Escucha `CitaAgendada` y envía un correo (capturado por Mailpit)   |

## Requisitos

- Docker y Docker Compose (o GitHub Codespaces, ver abajo)

Para desarrollo local sin Docker: Java 21+ y Node.js 22+.

## Levantar con Docker

```bash
docker compose up --build
```

| Servicio      | URL                                    |
|---------------|----------------------------------------|
| Frontend      | http://localhost:3000                  |
| Backend (API) | http://localhost:8080/api/citas        |
| Health        | http://localhost:8080/actuator/health  |
| Módulos       | http://localhost:8080/actuator/modulith|
| Mailpit (UI)  | http://localhost:8025                  |
| PostgreSQL    | localhost:5432                         |

Los valores de BD, correo y puertos se leen del archivo `.env` (no versionado). Si no existe, se usan los valores por defecto: BD/usuario/contraseña `citas` y los puertos de la tabla.

Para detener: `docker compose down` (añade `-v` para borrar los datos).

## GitHub Codespaces

1. En GitHub: **Code → Codespaces → Create codespace on main**.
2. Al arrancar, el Codespace ejecuta `docker compose up -d --build` automáticamente (tarda 2–4 minutos la primera vez).
3. Abre la pestaña **Ports** y entra al puerto **3000 (Frontend)**. Si aparece *HTTP 401*, cambia la visibilidad del puerto a **Public**.

Si un Codespace se creó antes de un cambio en `.devcontainer.json`, usa **Codespaces: Rebuild Container** desde la paleta de comandos.

## Desarrollo local

```bash
# Infraestructura
docker compose up -d db mailpit

# Backend (http://localhost:8080)
cd backend
./mvnw spring-boot:run

# Frontend (http://localhost:5173, redirige /api al backend)
cd frontend
npm install
npm run dev
```

## API

| Método | Ruta             | Descripción                                 |
|--------|------------------|---------------------------------------------|
| GET    | `/api/pacientes` | Listar pacientes                            |
| POST   | `/api/pacientes` | Registrar paciente                          |
| GET    | `/api/medicos`   | Listar médicos                              |
| POST   | `/api/medicos`   | Registrar médico                            |
| GET    | `/api/citas`     | Listar citas                                |
| POST   | `/api/citas`     | Agendar cita (envía correo de confirmación) |

```bash
curl -X POST http://localhost:8080/api/citas \
  -H "Content-Type: application/json" \
  -d '{"pacienteId":1,"medicoId":1,"fechaHora":"2026-11-01T10:00:00","emailContacto":"ana@example.com"}'
```

El correo aparece en Mailpit: http://localhost:8025

## Integración continua

- `docker-compose.yml` (workflow): ejecuta `mvnw package`, levanta los cuatro servicios y comprueba BD, API, envío de correo a Mailpit y frontend.
- `devcontainer.yml` (workflow): construye el devcontainer como lo hace Codespaces y verifica que el stack arranca solo.
