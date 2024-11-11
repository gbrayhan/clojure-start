#!/bin/bash
set -e

# Esperar a que la base de datos esté lista
echo "Esperando a que PostgreSQL esté disponible..."
while ! nc -z db 5432; do
  sleep 0.1
done
echo "PostgreSQL está disponible."

# Ejecutar migraciones
echo "Ejecutando migraciones..."
java -jar mi-api-rest.jar migrate

# Iniciar la aplicación
echo "Iniciando la aplicación..."
java -jar mi-api-rest.jar
