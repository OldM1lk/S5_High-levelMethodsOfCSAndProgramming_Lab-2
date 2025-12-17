#!/bin/bash
set -e

# Переход в корень проекта относительно папки scripts
cd "$(dirname "$0")/.."

# Сборка проекта через Maven (с тестами)
mvn clean package

echo "Сборка завершена: target/$(ls target | grep '.jar')"
