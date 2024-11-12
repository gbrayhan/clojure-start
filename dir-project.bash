#!/bin/bash

# Verifica si el directorio actual es un repositorio Git
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "Este script debe ejecutarse dentro de un repositorio Git."
    exit 1
fi

# Obtiene la lista de archivos no ignorados por .gitignore
# Usamos -z para manejar correctamente nombres con espacios y caracteres especiales
git ls-files --cached --others --exclude-standard -z | while IFS= read -r -d '' file; do
    echo "Archivo: $file"
    echo "Contenido:"
    echo "----------------------------------------"
    cat "$file"
    echo "----------------------------------------"
    echo -e "\n"
done
