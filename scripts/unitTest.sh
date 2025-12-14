#!/bin/bash
set -e

# Переход в корень проекта
cd "$(dirname "$0")/.." || exit

# Запуск юнит-тестов
mvn test
