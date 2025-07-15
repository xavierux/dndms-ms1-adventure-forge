# D&D Microservices - MS1: Adventure Forge (`dndms-ms1-adventure-forge`)

## 🧭 Propósito
Este microservicio es el punto de partida para la generación de contenido en el sistema "Forjador de Aventuras D&D". Su principal responsabilidad es crear las "semillas" de aventura y publicarlas para que otros microservicios puedan actuar sobre ellas.

## 🧱 Responsabilidades Clave
- Generar aleatoriamente los atributos de una nueva aventura.
- Construir un objeto `AventuraCreadaEvent` con los siguientes detalles:
  - `adventureId` (String, UUID)
  - `challengeType` (ej: "investigar", "recuperar", "proteger")
  - `environment` (ej: "ruina olvidada", "bosque encantado")
  - `numEncounters` (int)
  - `goldRewardTier` (ej: "poor", "generous", "treasure")
- Publicar `AventuraCreadaEvent` al bus de eventos Kafka.

---
## ⚙️ Stack Tecnológico
- **Lenguaje/Framework:** Java 17, Spring Boot 3.3.0
- **Gestión de Dependencias:** Maven
- **Comunicación de Eventos:** Spring Kafka (Productor)
- **DTOs Compartidos:** Consumidos como un Git Submodule desde el repositorio `dndms-event-dtos` (ubicado en `shared-dtos-module`).
- **Contenerización:** Docker

---
## 📤 Arquitectura de Eventos

### Eventos Publicados
- **Evento:** `AventuraCreadaEvent`
  - **Topic Kafka:** `aventuras-topic`
  - **Clave Kafka:** `adventureId`

### Eventos Consumidos
- Ninguno.

---
## 📡 API Endpoints
La API se expone bajo la ruta base `/api/v1/adventures`.

* `POST /generate-random`:
    * Dispara la generación de una nueva aventura con parámetros aleatorios.
    * Publica el `AventuraCreadaEvent` resultante a Kafka.
    * Devuelve el objeto `AventuraCreadaEvent` generado en la respuesta HTTP.

---
## 🐳 Entorno de Desarrollo y Configuración

Este repositorio contiene el `docker-compose.yml` principal que orquesta todo el ecosistema de desarrollo local, incluyendo los 4 microservicios y la infraestructura de soporte.

### Configuración
La aplicación utiliza un sistema de perfiles de Spring para gestionar la configuración:

* **`application.properties`**: Contiene la configuración para ejecutar localmente desde un IDE.
  - `server.port=8081`
  - `spring.kafka.producer.bootstrap-servers=localhost:9092`
* **`application-docker.properties`**: Anula propiedades para el entorno Docker. Se activa con el perfil `docker`.
  - `spring.kafka.producer.bootstrap-servers=kafka:29092`

### Cómo Construir y Ejecutar el Ecosistema Completo
El método recomendado es usar Docker Compose para levantar todos los servicios juntos.

1.  **Asegurar Submódulos:** Antes del primer build, o para obtener las últimas actualizaciones de los DTOs, ejecuta en la raíz de este proyecto:
    ```bash
    git submodule update --init --recursive
    ```
2.  **Construir e Iniciar:** Este comando construirá las imágenes Docker para todos los microservicios y los iniciará en segundo plano.
    ```bash
    # Desde la raíz de este repositorio (dndms-ms1-adventure-forge)
    docker-compose up -d --build
    ```
3.  **Verificar Estado:** Para ver si todos los contenedores están corriendo:
    ```bash
    docker-compose ps
    ```
4.  **Detener Entorno:**
    ```bash
    # Para detener los contenedores y mantener los datos (en volúmenes persistentes)
    docker-compose down

    # Para detener Y BORRAR todos los datos (reinicio limpio)
    docker-compose down -v
    ```
5.  **Ver Logs de un Servicio:**
    ```bash
    # Ejemplo para MS1
    docker-compose logs -f dndms-ms1-adventure-forge-app
    ```