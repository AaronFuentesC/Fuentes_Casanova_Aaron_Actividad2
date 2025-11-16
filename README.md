# API REST - Sistema de Reservas de Aulas

## Descripción
API REST desarrollada con Spring Boot para gestionar las reservas de aulas comunes de un centro educativo. Incluye autenticación JWT, control de acceso basado en roles y validaciones de negocio.

## Tecnologías Utilizadas
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** con JWT
- **Spring Data JPA**
- **H2 Database** (Base de datos en memoria)
- **Lombok**
- **ModelMapper**
- **Maven**

## Configuración y Ejecución

### Requisitos Previos
- JDK 17 o superior
- Maven 3.6+

### Instalación
```bash
# Clonar el repositorio
git clone <url-repositorio>
cd reserva-aulas

# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080`

### Consola H2
Accede a la consola H2 en: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:reservasdb`
- **Usuario**: `sa`
- **Contraseña**: (vacío)

## Usuarios de Prueba
Al iniciar la aplicación, se crean automáticamente dos usuarios:

| Email | Password | Rol |
|-------|----------|-----|
| admin@educativo.com | admin123 | ROLE_ADMIN |
| profesor@educativo.com | profesor123 | ROLE_PROFESOR |

## Endpoints de la API

### 🔐 Autenticación (`/auth`)

#### POST /auth/register
Registrar nuevo usuario
- **Acceso**: Público
- **Body**:
```json
{
  "nombre": "Juan Pérez",
  "role": "ROLE_PROFESOR",
  "email": "juan@educativo.com",
  "password": "123456"
}
```
- **Response**: Token JWT + datos usuario

#### POST /auth/login
Iniciar sesión
- **Acceso**: Público
- **Body**:
```json
{
  "email": "admin@educativo.com",
  "password": "admin123"
}
```
- **Response**: Token JWT + datos usuario

#### GET /auth/perfil
Obtener perfil del usuario autenticado
- **Acceso**: Autenticado (ADMIN, PROFESOR)
- **Headers**: `Authorization: Bearer <token>`
- **Response**: Datos del usuario

---

### 👤 Usuarios (`/usuario`)

#### DELETE /usuario/{id}
Eliminar usuario
- **Acceso**: ROLE_ADMIN
- **Headers**: `Authorization: Bearer <token>`

#### PUT /usuario/{id}
Modificar datos de usuario
- **Acceso**: ROLE_ADMIN
- **Headers**: `Authorization: Bearer <token>`
- **Body**:
```json
{
  "nombre": "Nuevo Nombre",
  "email": "nuevo@email.com"
}
```

#### PATCH /usuario/cambiar-pass
Cambiar contraseña del usuario autenticado
- **Acceso**: Autenticado (ADMIN, PROFESOR)
- **Headers**: `Authorization: Bearer <token>`
- **Body**:
```json
{
  "passwordActual": "password123",
  "passwordNueva": "newpassword456"
}
```

---

### 🏫 Aulas (`/aulas`)

#### GET /aulas
Listar todas las aulas
- **Acceso**: Autenticado (ADMIN, PROFESOR)
- **Query Params opcionales**:
    - `?capacidad=30` - Filtrar por capacidad mínima
    - `?ordenadores=true` - Filtrar aulas con ordenadores

#### GET /aulas/{id}
Obtener detalles de un aula
- **Acceso**: Autenticado (ADMIN, PROFESOR)

#### POST /aulas
Crear nueva aula
- **Acceso**: ROLE_ADMIN
- **Body**:
```json
{
  "nombre": "Aula 102",
  "capacidad": 30,
  "esAulaDeOrdenadores": false,
  "numeroOrdenadores": 0
}
```

#### PUT /aulas/{id}
Modificar aula
- **Acceso**: ROLE_ADMIN
- **Body**: Mismo formato que POST

#### DELETE /aulas/{id}
Eliminar aula
- **Acceso**: ROLE_ADMIN

#### GET /aulas/{id}/reservas
Ver todas las reservas de un aula específica
- **Acceso**: Autenticado (ADMIN, PROFESOR)

---

### 📅 Reservas (`/reservas`)

#### GET /reservas
Listar todas las reservas (incluye aula, tramo horario y usuario)
- **Acceso**: Autenticado (ADMIN, PROFESOR)

#### GET /reservas/{id}
Obtener una reserva específica
- **Acceso**: Autenticado (ADMIN, PROFESOR)

#### POST /reservas
Crear nueva reserva
- **Acceso**: Autenticado (ADMIN, PROFESOR)
- **Body**:
```json
{
  "fecha": "2025-12-15",
  "motivo": "Clase de programación",
  "numeroAsistentes": 25,
  "aulaId": 1,
  "tramoHorarioId": 1
}
```
- **Validaciones aplicadas**:
    - ✅ Fecha no puede ser en el pasado
    - ✅ No puede solaparse con otra reserva (misma aula, tramo y fecha)
    - ✅ Número de asistentes no puede superar capacidad del aula

#### DELETE /reservas/{id}
Eliminar reserva
- **Acceso**:
    - ROLE_ADMIN: Puede eliminar cualquier reserva
    - ROLE_PROFESOR: Solo puede eliminar sus propias reservas

---

### ⏰ Tramos Horarios (`/tramo-horario`)

#### GET /tramo-horario
Listar todos los tramos horarios
- **Acceso**: Autenticado (ADMIN, PROFESOR)

#### GET /tramo-horario/{id}
Obtener un tramo horario específico
- **Acceso**: Autenticado (ADMIN, PROFESOR)

#### POST /tramo-horario
Crear nuevo tramo horario
- **Acceso**: ROLE_ADMIN
- **Body**:
```json
{
  "diaSemana": "LUNES",
  "sesionDia": 1,
  "horaInicio": "08:00:00",
  "horaFin": "09:00:00",
  "tipo": "LECTIVA"
}
```

#### DELETE /tramo-horario/{id}
Eliminar tramo horario
- **Acceso**: ROLE_ADMIN

---

## DTOs Utilizados

### AuthResponse
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "admin@educativo.com",
  "nombre": "Admin Principal",
  "role": "ROLE_ADMIN"
}
```

### AulaDTO
```json
{
  "id": 1,
  "nombre": "Aula 101",
  "capacidad": 30,
  "esAulaDeOrdenadores": false,
  "numeroOrdenadores": 0
}
```

### ReservaDTO
```json
{
  "id": 1,
  "fecha": "2025-12-15",
  "motivo": "Clase de matemáticas",
  "numeroAsistentes": 25,
  "fechaCreacion": "2025-11-14",
  "aula": { /* AulaDTO */ },
  "tramoHorario": { /* TramoHorarioDTO */ },
  "usuario": { /* UsuarioDTO */ }
}
```

### TramoHorarioDTO
```json
{
  "id": 1,
  "diaSemana": "LUNES",
  "sesionDia": 1,
  "horaInicio": "08:00:00",
  "horaFin": "09:00:00",
  "tipo": "LECTIVA"
}
```

### UsuarioDTO
```json
{
  "id": 1,
  "nombre": "Admin Principal",
  "role": "ROLE_ADMIN",
  "email": "admin@educativo.com"
}
```

---

## Enumeraciones

### Role
- `ROLE_ADMIN` - Administrador (acceso completo)
- `ROLE_PROFESOR` - Profesor (acceso limitado)

### DiaSemana
- `LUNES`, `MARTES`, `MIERCOLES`, `JUEVES`, `VIERNES`

### TipoTramo
- `LECTIVA` - Hora de clase
- `RECREO` - Descanso
- `MEDIODIA` - Hora del almuerzo

---

## Validaciones Implementadas

### Reservas
1. ✅ **No solapamiento**: No se permite reservar la misma aula, en el mismo tramo horario y fecha
2. ✅ **Fecha futura**: No se pueden hacer reservas en el pasado
3. ✅ **Capacidad**: El número de asistentes no puede superar la capacidad del aula
4. ✅ **Autorización**: Solo el creador o un ADMIN puede eliminar una reserva

### Usuarios
1. ✅ **Email único**: No se permiten emails duplicados
2. ✅ **Contraseña**: Mínimo 6 caracteres
3. ✅ **Email válido**: Formato de email correcto

### Campos obligatorios
Todos los DTOs tienen validaciones con mensajes personalizados usando anotaciones de Jakarta Validation.

---

## Manejo de Errores

La API incluye un **GlobalExceptionHandler** que devuelve respuestas JSON estructuradas:

```json
{
  "timestamp": "2025-11-14T10:30:00",
  "status": 400,
  "mensaje": "Error de validación",
  "errores": {
    "campo": "mensaje de error"
  }
}
```

### Tipos de respuestas de error:
- **400 Bad Request**: Errores de validación
- **401 Unauthorized**: Credenciales incorrectas
- **403 Forbidden**: Sin permisos suficientes
- **404 Not Found**: Recurso no encontrado
- **409 Conflict**: Email duplicado
- **500 Internal Server Error**: Error del servidor

---

## Ejemplos de Uso con cURL

### Registrar usuario
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María López",
    "role": "ROLE_PROFESOR",
    "email": "maria@educativo.com",
    "password": "123456"
  }'
```

### Iniciar sesión
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@educativo.com",
    "password": "admin123"
  }'
```

### Crear reserva (con token)
```bash
curl -X POST http://localhost:8080/reservas \
  -H "Authorization: Bearer <tu-token-aqui>" \
  -H "Content-Type: application/json" \
  -d '{
    "fecha": "2025-12-20",
    "motivo": "Examen final",
    "numeroAsistentes": 30,
    "aulaId": 1,
    "tramoHorarioId": 1
  }'
```

### Listar aulas con ordenadores
```bash
curl -X GET "http://localhost:8080/aulas?ordenadores=true" \
  -H "Authorization: Bearer <tu-token-aqui>"
```

---

## Estructura del Proyecto

```
src/main/java/com/aaronfuentescasanova/actividad2/
├── config/              # Configuraciones (DataInitializer)
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
├── entity/              # Entidades JPA
├── enums/               # Enumeraciones
├── exception/           # Excepciones personalizadas
├── repository/          # Repositorios JPA
├── security/            # Configuración de seguridad y JWT
└── service/             # Lógica de negocio
```

---

## Autor
Aarón Fuentes Casanova

## Licencia
Este proyecto es de uso educativo.