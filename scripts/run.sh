#!/bin/bash
set -e

# Переход в корень проекта
cd "$(dirname "$0")/.." || exit

# Если jar ещё не собран — собрать
if [ ! -d "target" ] || [ -z "$(ls target | grep '.jar')" ]; then
  bash scripts/build.sh
fi

JAR_FILE=$(ls target | grep '.jar' | head -n 1)

# Проброс аргументов в приложение
java -jar "target/$JAR_FILE" "$@"
