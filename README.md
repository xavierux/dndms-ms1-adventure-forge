# D&D Microservices - MS1: Adventure Forge (`dndms-ms1-adventure-forge`)

## Propósito
Este microservicio es responsable de generar las misiones o aventuras base del sistema.
Puede operar en modo manual (recibiendo parámetros) o aleatorio.

## Responsabilidades Clave
- Generar el objeto `AventuraCreadaEvent` con:
    - `adventureId` (String)
    - `challengeType` (String)
    - `environment` (String)
    - `numEncounters` (int)
    - `goldRewardTier` (String)
- Publicar `AventuraCreadaEvent` al bus de eventos.

## Tecnologías
- Java, Spring Boot
- Spring Kafka (Productor)
- DTOs compartidos vía Git Submodule (`dndms-event-dtos`)

## Eventos Publicados
- `AventuraCreadaEvent` (al topic: `aventuras-topic`)

## Eventos Consumidos
- Ninguno

## API Endpoints
- `POST /api/v1/adventures` (para creación manual)
- `POST /api/v1/adventures/random` (para creación aleatoria)
*(Estos se definirán e implementarán más adelante)*

## Cómo Construir y Ejecutar Localmente
1. Asegúrate de que los submódulos Git estén inicializados y actualizados:
   `git submodule init`
   `git submodule update --remote`
2. Construye con Maven:
   `mvn clean package`
3. Ejecuta la aplicación (requiere Kafka corriendo, ver `docker-compose.yml`):
   `java -jar target/dndms-ms1-adventure-forge-0.0.1-SNAPSHOT.jar`
4. O ejecuta usando el perfil de Spring Boot en tu IDE.

## Entorno de Desarrollo Local (`docker-compose.yml`)
Este repositorio contiene el `docker-compose.yml` principal para levantar el entorno de desarrollo local (Kafka, Zookeeper, Redis, etc.).
Ejecutar con: `docker-compose up -d`