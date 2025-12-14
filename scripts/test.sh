#!/bin/bash
set -e

# Переход в корень проекта
cd "$(dirname "$0")/.." || exit

# Полный прогон тестов и проверок
mvn verify
