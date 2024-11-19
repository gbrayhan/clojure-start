#!/bin/bash

if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "This script must be run inside a Git repository."
    exit 1
fi

git ls-files --cached --others --exclude-standard -z | while IFS= read -r -d '' file; do
    echo "File: $file"
    echo "Content:"
    echo "----------------------------------------"
    cat "$file"
    echo "----------------------------------------"
    echo -e "\n"
done
