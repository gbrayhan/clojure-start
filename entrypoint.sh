#!/bin/bash
set -e

echo "Waiting for PostgreSQL to be available..."
while ! nc -z db 5432; do
  sleep 0.1
done
echo "PostgreSQL is available."

echo "Running migrations..."
ls -laF
whoami
pwd
java -jar my-api-rest.jar migrate

echo "Starting the application..."
java -jar my-api-rest.jar
