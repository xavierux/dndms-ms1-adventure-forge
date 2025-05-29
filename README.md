# D&D Microservices - MS1: Adventure Forge (`dndms-ms1-adventure-forge`)

## 🧭 Propósito
Este microservicio es el punto de partida para la generación de contenido en el sistema "Forjador de Aventuras D&D".  
Crea "semillas" de aventura y las publica para que otros microservicios actúen sobre ellas.

## 🧱 Responsabilidades Clave
- Generar aleatoriamente atributos de una aventura.
- Construir un objeto `AventuraCreadaEvent` con:
  - `adventureId` (UUID)
  - `challengeType` (ej: "investigar", "recuperar", "proteger")
  - `environment` (ej: "ruina olvidada", "bosque encantado")
  - `numEncounters` (int)
  - `goldRewardTier` (ej: "poor", "generous", "treasure")
- Publicar `AventuraCreadaEvent` a Kafka.

## ⚙️ Stack Tecnológico
- **Lenguaje/Framework:** Java 17, Spring Boot 3.3.0
- **Dependencias:** Maven
- **Eventos:** Spring Kafka (Productor)
- **DTOs Compartidos:** Submódulo Git `dndms-event-dtos`  
  Paquete: `com.xvclemente.dnd.dtos.events`
- **Contenerización:** Docker + `docker-compose.yml`  
  (Dockerfile pendiente)

## 📤 Eventos Publicados
- **Evento:** `AventuraCreadaEvent`
  - **Topic Kafka:** `aventuras-topic`
  - **Clave Kafka:** `adventureId`
  - **Payload:** detalles de la aventura generada

## 📥 Eventos Consumidos
- Ninguno

## 📡 API Endpoints
- `POST /api/v1/adventures/generate-random`
  - Genera una aventura aleatoria
  - Publica evento en Kafka
  - Devuelve `AventuraCreadaEvent` como respuesta
- `POST /api/v1/adventures` *(manual, por implementar)*

## 🔧 Configuración Local (`application.properties`)
```properties
server.port=8081
spring.kafka.producer.bootstrap-servers=localhost:9092
app.kafka.topic.aventuras-creadas=aventuras-topic
```

## 🐳 Entorno de Desarrollo (`docker-compose.yml`)
Incluye:
- Zookeeper
- Kafka
- Redis
- DynamoDB Local
- Kafdrop

**Levantar entorno:**
```bash
docker-compose up -d
```

**Detener entorno:**
```bash
docker-compose down
```

**Kafdrop UI:**  
http://localhost:9000

## 🛠 Cómo Ejecutar Localmente (MS1)

**1. Clonar con submódulos:**
```bash
git clone --recurse-submodules <URL_REPO> dndms-ms1-adventure-forge
cd dndms-ms1-adventure-forge
```

**2. Inicializar submódulos:**
```bash
git submodule init
git submodule update --remote shared-dtos-module
```

**3. Verificar servicios:**
```bash
docker-compose ps
```

**4. Construir app (opcional):**
```bash
mvn clean package
```

**5. Ejecutar MS1:**
- **Opción A (dev):**
  ```bash
  mvn spring-boot:run
  ```
- **Opción B (JAR):**
  ```bash
  java -jar target/dndms-ms1-adventure-forge-0.0.1-SNAPSHOT.jar
  ```
- **Opción C (IDE):**  
  Ejecuta `Ms1AdventureForgeApplication.java` desde tu IDE.

**6. Probar endpoint:**
```bash
curl -X POST http://localhost:8081/api/v1/adventures/generate-random
```

**7. Verificar en Kafdrop:**  
http://localhost:9000 → `aventuras-topic` → "View Messages"

## 📌 Próximos Pasos
- [ ] Implementar `POST /api/v1/adventures`
- [ ] Añadir variedad a la generación aleatoria
- [ ] Mejorar validaciones y manejo de errores
- [ ] Tests unitarios y de integración
- [ ] Crear `Dockerfile`

## 🤝 Contribución
Lee `CONTRIBUTING.md` para más detalles.

## 📄 Licencia
Este proyecto está bajo la Licencia XYZ. Ver `LICENSE.md`.
