#!/bin/bash

#Сборка проекта
bash scripts/build.sh

# Переход в корень проекта
cd "$(dirname "$0")/.." || exit

# Проброс аргументов в программу
java -cp "out/app.jar;lib/kotlinx-cli-jvm-0.3.6.jar;lib/h2-2.4.240.jar" presentation/MainKt "$@"