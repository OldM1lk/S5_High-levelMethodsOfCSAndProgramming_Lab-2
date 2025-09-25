#!/bin/bash

#Сборка проекта
bash build.sh

# Проброс аргументов в программу
java -cp "out/app.jar;lib/kotlinx-cli-jvm-0.3.6.jar" MainKt "$@"