# Etapa de construcción usando Leiningen
FROM clojure:lein-2.9.7 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo project.clj y descargar dependencias
COPY project.clj /app/
RUN lein deps

# Copiar el resto del código fuente
COPY . /app/

# Construir el uberjar, listar el contenido y verificar su existencia
RUN lein uberjar && \
    echo "Contenido de /app/target/uberjar:" && \
    ls -l /app/target/uberjar && \
    if [ ! -f /app/target/uberjar/mi-api-rest-0.1.0-SNAPSHOT.jar ]; then \
        echo "Error: El archivo JAR no se encontró en /app/target/uberjar"; \
        exit 1; \
    fi

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Instalar netcat
RUN apt-get update && \
    apt-get install -y netcat && \
    rm -rf /var/lib/apt/lists/*

# Establecer el directorio de trabajo
WORKDIR /app

COPY --from=build /app/target/uberjar/*.jar /app/mi-api-rest.jar

# Copiar las migraciones y configuración
COPY --from=build /app/migrations /app/migrations
COPY --from=build /app/resources/migratus.edn /app/resources/migratus.edn

# Copiar el script de entrypoint
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Exponer el puerto de la aplicación
EXPOSE 3000

# Definir el entrypoint
ENTRYPOINT ["./entrypoint.sh"]
