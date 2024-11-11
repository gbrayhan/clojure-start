# Etapa de construcción usando Leiningen
FROM clojure:lein-2.9.7 as build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo project.clj y descargar dependencias
COPY project.clj /app/
RUN lein deps

# Copiar el resto del código fuente
COPY . /app/

# Construir el uberjar
RUN lein uberjar

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el uberjar desde la etapa de construcción
COPY --from=build /app/target/uberjar/mi-api-rest-0.1.0-SNAPSHOT-standalone.jar /app/mi-api-rest.jar

# Copiar las migraciones y configuración
COPY --from=build /app/migrations /app/migrations
COPY --from=build /app/resources/migratus.edn /app/migratus.edn

# Copiar el script de entrypoint
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Exponer el puerto de la aplicación
EXPOSE 3000

# Definir el entrypoint
ENTRYPOINT ["./entrypoint.sh"]
