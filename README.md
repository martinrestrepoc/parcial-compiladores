# Parcial Compiladores - Backend API

Backend en Spring Boot para gestionar peleadores, peleas y ejecución de peleas con apoyo de IA.

Este README está escrito para servir como **contexto técnico completo** para desarrollar un frontend (humano o asistido por IA).

## 1. Stack técnico

- Java 17
- Spring Boot 4.0.3
- Spring Web MVC
- Spring Data JPA
- Spring Security (JWT stateless)
- PostgreSQL
- Validación con Jakarta Validation
- OpenAPI/Swagger (`springdoc-openapi`)
- LangChain4j + OpenAI (`gpt-4o-mini`)

## 2. Configuración actual

Archivo: `src/main/resources/application.yml`

- Puerto: `8050`
- DB: PostgreSQL en `localhost:5432/springboot_db`
- JPA: `ddl-auto: update`
- Modelo IA:
  - `langchain4j.open-ai.chat-model.api-key: demo`
  - `langchain4j.open-ai.chat-model.model-name: gpt-4o-mini`

## 3. Autenticación y seguridad

### 3.1 Mecanismo

- Login por `POST /auth/login`
- Respuesta: **JWT en texto plano**
- Endpoints protegidos: todo excepto rutas públicas listadas abajo
- Header requerido en protegidos:

```http
Authorization: Bearer <token>
```

### 3.2 Rutas públicas (sin JWT)

- `/auth/**`
- `/public/**`
- `/swagger-ui/**`
- `/swagger-ui.html`
- `/v3/api-docs/**`

### 3.3 Usuario semilla

Al iniciar la app se crea automáticamente si no existe:

- username: `simon maligno`
- password: `Simon123`

## 4. Modelos de dominio

## 4.1 Fighter

Representa un peleador.

Campos:

- `id: Long`
- `name: String`
- `strength: int` (0-100)
- `speed: int` (0-100)
- `stamina: int` (0-100)
- `specialSkill: String`
- `bio: String` (max 2000)
- `backstory: String` (max 4000)
- `wins: int`
- `losses: int`

Notas:

- Al crear un fighter, `wins=0` y `losses=0`.

## 4.2 Fight

Representa una pelea entre dos fighters.

Campos persistidos:

- `id: Long`
- `fighter1: Fighter`
- `fighter2: Fighter`
- `status: FightStatus` (`PENDING` | `EXECUTED`)
- `winner: Fighter` (persistido internamente)
- `createdAt: Instant`
- `executedAt: Instant?`
- `resultSummary: String` (max 2000)

Importante para frontend:

- `winner` está marcado con `@JsonIgnore`, por lo tanto **no se envía en respuestas JSON**.
- El ganador se expresa solo en el texto de `resultSummary` generado por IA.

## 5. Contratos DTO

## 5.1 Auth

### LoginRequest

```json
{
  "username": "simon maligno",
  "password": "Simon123"
}
```

Respuesta exitosa:

- `200 OK`
- body: string JWT

## 5.2 Fighters

### FighterCreateDTO / FighterUpdateDTO

```json
{
  "name": "Rayo Chiquito",
  "strength": 82,
  "speed": 95,
  "stamina": 70,
  "specialSkill": "Finta relampago",
  "bio": "Velocista tactico",
  "backstory": "Criado en callejones"
}
```

## 5.3 Fights

### FightCreateDTO / FightUpdateDTO

```json
{
  "fighter1Id": 1,
  "fighter2Id": 2,
  "resultSummary": "Pelea agendada para semifinal"
}
```

### FightExecuteDTO

```json
{
  "context": "Final del torneo, publico hostil y mucha presion"
}
```

### FightExecuteResponseDTO (respuesta de execute)

```json
{
  "id": 3,
  "status": "EXECUTED",
  "resultSummary": "Ganó ... porque ...",
  "executedAt": "2026-03-02T20:34:30.045051Z"
}
```

## 6. Endpoints

Base URL local:

```text
http://localhost:8050
```

## 6.1 Auth

### POST `/auth/login` (público)

Body:

```json
{
  "username": "simon maligno",
  "password": "Simon123"
}
```

Responses:

- `200`: JWT string
- Si usuario/clave inválida actualmente lanza `RuntimeException` (no mapeada en handler global), por lo que puede verse como `500`.

## 6.2 AI diagnóstico

### GET `/public/ai/hello` (público)

Prueba rápida de conectividad con IA.

Response éxito:

```json
{
  "ok": "true",
  "message": "Hola ..."
}
```

Response error IA:

```json
{
  "ok": "false",
  "error": "<TipoExcepcion>",
  "message": "<mensaje>"
}
```

## 6.3 Fighters (protegido con JWT)

### POST `/api/fighters`

Crea fighter.

- `201 Created`
- Retorna entidad `Fighter` completa.

### GET `/api/fighters`

Lista fighters.

- `200 OK`
- Retorna `Fighter[]`

### GET `/api/fighters/{id}`

Obtiene fighter por id.

- `200 OK`
- `404 Not Found` si no existe

### PUT `/api/fighters/{id}`

Actualiza fighter.

- `200 OK`
- `404 Not Found` si no existe
- `400 Bad Request` si falla validación

### DELETE `/api/fighters/{id}`

Elimina fighter.

- `204 No Content`
- `404 Not Found` si no existe

## 6.4 Fights (protegido con JWT)

### POST `/api/fights`

Crea pelea en estado `PENDING`.

Reglas:

- `fighter1Id` y `fighter2Id` deben existir
- No pueden ser iguales

Respuestas:

- `201 Created`
- `400` si ambos ids son iguales
- `404` si algún fighter no existe

### GET `/api/fights`

Lista peleas.

- `200 OK`
- Retorna `Fight[]` (sin campo `winner`)

### GET `/api/fights/{id}`

Obtiene pelea por id.

- `200 OK`
- `404 Not Found`

### PUT `/api/fights/{id}`

Actualiza pelea solo si está `PENDING`.

Reglas:

- Si ya está `EXECUTED` => `400`
- fighters deben ser distintos

Respuestas:

- `200 OK`
- `400 Bad Request`
- `404 Not Found`

### DELETE `/api/fights/{id}`

Elimina pelea.

- `204 No Content`
- `404 Not Found`

### POST `/api/fights/{id}/execute`

Ejecuta pelea usando IA y deja estado `EXECUTED`.

Flujo interno:

1. Verifica que exista la pelea
2. Verifica que no esté ejecutada
3. Construye prompt con stats/historias de ambos fighters + `context`
4. Pide a IA que diga quién ganó y por qué
5. Guarda la respuesta textual en `resultSummary`
6. Marca `executedAt`

Respuestas:

- `200 OK` con `FightExecuteResponseDTO`
- `400` si ya estaba ejecutada
- `404` si no existe

Nota:

- Si la IA falla (moderación, red, auth, etc.), se guarda en `resultSummary` un texto con el error.

## 7. Errores y formato

Handler global (`ApiError`) para:

- `NotFoundException` -> `404`
- `BadRequestException` -> `400`
- `MethodArgumentNotValidException` -> `400` con `fieldsErrors`

Formato típico:

```json
{
  "timestamp": "2026-03-02T20:00:00Z",
  "status": 400,
  "error": "A fight requires two different fighters",
  "path": "/api/fights",
  "fieldsErrors": {
    "name": "Name is required"
  }
}
```

`fieldsErrors` aparece solo en errores de validación de DTO.

## 8. Swagger

- UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`

## 9. Guía rápida para frontend

Flujo recomendado:

1. Login (`/auth/login`) y guardar token
2. Consultar/crear fighters
3. Crear fight (`/api/fights`)
4. Ejecutar fight (`/api/fights/{id}/execute`)
5. Mostrar `resultSummary` como narrativa del resultado

Puntos importantes de UI:

- No esperar campo `winner` en respuesta JSON.
- Mostrar estados de pelea (`PENDING` / `EXECUTED`).
- Mostrar errores de API tal como vienen en `error` o `fieldsErrors`.
- Para depurar IA, usar `/public/ai/hello`.

## 10. Ejemplos cURL

### Login

```bash
curl -X POST http://localhost:8050/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"simon maligno","password":"Simon123"}'
```

### Crear fighter

```bash
curl -X POST http://localhost:8050/api/fighters \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Rayo Chiquito",
    "strength":82,
    "speed":95,
    "stamina":70,
    "specialSkill":"Finta relampago",
    "bio":"Velocista tactico",
    "backstory":"Criado en callejones"
  }'
```

### Crear fight

```bash
curl -X POST http://localhost:8050/api/fights \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"fighter1Id":1,"fighter2Id":2,"resultSummary":"Pelea agendada"}'
```

### Execute fight

```bash
curl -X POST http://localhost:8050/api/fights/1/execute \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"context":"Final del torneo"}'
```

## 11. Estado actual y límites conocidos

- `api-key: demo` funciona para pruebas en ciertos entornos, pero puede fallar por límites/moderación.
- El login inválido hoy no está mapeado por el `GlobalExceptionHandler` (usa `RuntimeException`).
- `winner` se persiste internamente pero no se expone en la API.

