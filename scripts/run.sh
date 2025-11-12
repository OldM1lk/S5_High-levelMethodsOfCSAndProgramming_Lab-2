#!/bin/bash

#Сборка проекта
bash build.sh

# Переход в корень проекта
cd "$(dirname "$0")/.." || exit

# Проброс аргументов в программу
java -cp "out/app.jar;lib/kotlinx-cli-jvm-0.3.6.jar" presentation/MainKt "$@"