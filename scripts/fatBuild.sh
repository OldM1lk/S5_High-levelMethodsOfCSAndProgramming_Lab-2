#!/bin/bash
set -e

# Переход в корень проекта относительно папки scripts
cd "$(dirname "$0")/.."

# Сборка fat-jar через Maven (без тестов)
mvn clean package -DskipTests

echo "Fat-jar собран: target/$(ls target | grep '.jar')"
