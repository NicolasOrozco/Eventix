# Eventix - Sistema de Gestión de Eventos y Boletas 🎟️

### Proyecto Final - Ingeniería de Software | Universidad del Quindío (2026)

Este repositorio contiene el **Producto Mínimo Viable (MVP)** funcional para **Eventix**, un sistema diseñado bajo los lineamientos académicos de la Universidad del Quindío.

El sistema integra una transacción compleja del núcleo del negocio (compra de boletas vinculando múltiples entidades core) y el control completo (CRUD) de la entidad principal de Eventos, garantizando trazabilidad desde el modelado hasta la implementación.

---

## 🚀 Características del MVP

El sistema consta de **dos pantallas ejecutables integradas en una interfaz web moderna y fluida** (servida directamente por la aplicación):

### 1. Pantalla: Transacción de Negocio (Emisión de Boletas)

- Formulario inteligente que asocia un `Cliente` existente con un `Evento` activo.
- Permite seleccionar cantidad de boletas (**máximo 5 por transacción**) y tipo de localidad (**General o VIP**).
- Genera comprobantes dinámicos con códigos únicos (`EVX-XXXXXXXX`).
- Descuenta automáticamente el inventario disponible utilizando `@Transactional`.

### 2. Pantalla: Módulo de Gestión de Eventos (CRUD)

#### Listar
- Visualización dinámica en tabla.
- Indicadores visuales de estado (Habilitado / Cancelado).
- Balance de cupos disponible.

#### Crear
- Validaciones automáticas:
    - Precio > 0
    - Fechas futuras
- Inicializa stock al 100% de capacidad.

#### Actualizar
- Modificación segura de:
    - Datos básicos
    - Capacidad
    - Estado operativo (Activar / Cancelar)

#### Eliminar
- Impide eliminar eventos con boletas vendidas.
- Protección de integridad referencial.

---

## 🛠️ Stack Tecnológico

Arquitectura diseñada para ser ligera, autocontenida y ejecutable localmente.

### Backend
- Java 17+
- Spring Boot 3.x
    - Spring Web
    - Spring Data JPA

### Base de datos
- H2 Database (en memoria)

### Frontend
- HTML5
- Tailwind CSS (CDN)
- JavaScript Vanilla
- API Fetch asíncrona

---

## 📐 Reglas de Negocio e Invariantes Implementadas

Las reglas se validan en `EventixService.java`.

| Código | Regla |
|----------|----------|
| RN-01 | No emitir boletas superiores a la capacidad disponible |
| RN-02 | La fecha del evento debe ser futura |
| RN-03 / RF-07 | Cada boleto posee un código único |
| RN-04 | Máximo 5 boletas por operación |
| RN-05 / RF-08 | Registrar fecha exacta del sistema |
| RN-06 | Precio VIP > Precio General |
| RF-09 | Bloqueo de compras para eventos cancelados |

---

## 📋 Matriz de Trazabilidad Simplificada

| Requisito | Caso de Uso | Componente UI | Endpoint | Método |
|------------|------------|------------|------------|------------|
| RF-01 / RF-02 | CU-02 Gestionar Evento | Formulario Crear Evento | `/api/eventos` | POST |
| RF-03 | CU-02 Gestionar Evento | Tabla Eventos (Editar) | `/api/eventos/{id}` | PUT |
| RF-04 | CU-02 Gestionar Evento | Tabla Eventos (Eliminar) | `/api/eventos/{id}` | DELETE |
| RF-05 / RF-06 | CU-01 Comprar Boleto | Formulario Venta | `/api/eventos` | GET |
| RF-07 / RF-08 / RF-09 | CU-01 Comprar Boleto | Confirmar y Generar | `/api/boletos/comprar` | POST |

---

## 📂 Estructura del Proyecto

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── uniquindio/
    │           └── eventix/
    │               ├── EventixApplication.java
    │               ├── controller/
    │               │   ├── BoletoController.java
    │               │   └── EventoController.java
    │               ├── dto/
    │               │   └── CompraRequestDTO.java
    │               ├── model/
    │               │   ├── Boleto.java
    │               │   ├── Cliente.java
    │               │   ├── Estado.java
    │               │   └── Evento.java
    │               ├── repository/
    │               │   ├── BoletoRepository.java
    │               │   ├── ClienteRepository.java
    │               │   └── EventoRepository.java
    │               └── service/
    │                   └── EventixService.java
    └── resources/
        ├── application.properties
        └── static/
            └── index.html
```

---

## ⚙️ Instalación y Ejecución

### Prerrequisitos

Instalar:

- Java JDK 17+
- Apache Maven

También puede utilizar el wrapper incluido:

```bash
./mvnw
```

### Pasos

Clone o copie el proyecto respetando la estructura Maven estándar.

Abra una terminal en la raíz del proyecto y ejecute:

```bash
mvn spring-boot:run
```

Una vez iniciado Spring Boot, abra:

```text
http://localhost:8080/index.html
```

---

## 🗄️ Consola H2

Para inspeccionar directamente las tablas creadas:

**URL**

```text
http://localhost:8080/h2-console
```

**Parámetros**

```text
JDBC URL: jdbc:h2:mem:eventixdb
User Name: sa
Password:
```

(Dejar contraseña vacía)

---

## 🧪 Guía de Pruebas y Datos Iniciales

El sistema carga automáticamente datos para pruebas.

### Clientes Disponibles

| ID | Nombre |
|------|------|
| 1090274067 | Ana Torres |
| 1092852380 | Juan Pérez |
| 1111111111 | Laura Gómez |
| 2222222222 | Daniel Rojas |

---

## Escenarios de Prueba

### Flujo básico (Compra exitosa)

1. Ir a **Registrar Compra**
2. Ingresar:

```text
Cliente: 1090274067
Evento: Concierto Rock
Cantidad: 2
```

3. Presionar:

```text
Confirmar y Generar Boletas
```

Resultado esperado:

- Comprobante exitoso
- Códigos únicos generados
- Stock actualizado

---

### Regla RN-04 (Límite máximo)

Intentar:

```text
Cantidad = 6
```

Resultado esperado:

```text
Validación fallida:
No se permite comprar más de 5 boletas en una única transacción.
```

---

### Restricción RF-09 (Evento Cancelado)

1. Ir a:

```text
Gestión de Eventos
```

2. Seleccionar:

```text
Cine Bajo Las Estrellas
```

3. Intentar comprar entradas.

Resultado esperado:

- Compra rechazada.

Corrección:

1. Editar evento
2. Cambiar estado a:

```text
Activo / Habilitado
```

3. Guardar cambios

Resultado:

- Compra habilitada nuevamente.

---

### Regla RN-06 (Precio VIP)

Crear un evento con:

```text
Precio VIP <= Precio General
```

Resultado esperado:

- Persistencia bloqueada
- Validación de negocio activada

---

## Autor

Proyecto académico desarrollado por:
**- Leidy Suárez Álvarez**  
**- Nicolás Orozco Osorio**  

Desarrollado para:
**Ingeniería de Software**  
**Universidad del Quindío**  
**2026**