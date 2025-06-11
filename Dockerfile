# --- Etapa 1: Construcción (Build Stage) ---
# Usamos una imagen de Maven que ya tiene JDK 17 para compilar nuestro proyecto
FROM maven:3.9-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos primero el pom.xml para aprovechar el cache de capas de Docker.
# Si las dependencias no cambian, Docker no necesitará descargarlas de nuevo.
COPY pom.xml .

# Copiamos el submódulo de DTOs también
COPY shared-dtos-module ./shared-dtos-module

# Descargamos las dependencias
RUN mvn dependency:go-offline -B

# Copiamos el resto del código fuente
COPY src ./src

# Construimos la aplicación, saltando las pruebas para un build más rápido
RUN mvn package -DskipTests

# --- Etapa 2: Ejecución (Runtime Stage) ---
# Usamos una imagen base mucho más ligera que solo tiene el Java Runtime Environment (JRE)
FROM eclipse-temurin:17-jre-jammy

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR ejecutable que se construyó en la etapa anterior
COPY --from=build /app/target/dndms-ms1-adventure-forge-0.0.1-SNAPSHOT.jar app.jar

# Puerto que la aplicación expondrá dentro del contenedor
EXPOSE 8081

# Comando para ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]