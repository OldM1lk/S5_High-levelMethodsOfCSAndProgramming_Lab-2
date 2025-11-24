#!/bin/bash

#Сборка проекта
bash scripts/build.sh

# Переход в корень проекта
cd "$(dirname "$0")/.." || exit

if [[ "$OS" == "Windows_NT" ]]; then
    SEP=";"
else
    SEP=":"
fi

# Проброс аргументов в программу
java -cp "out/app.jar${SEP}lib/kotlinx-cli-jvm-0.3.6.jar${SEP}lib/h2-2.4.240.jar" presentation/MainKt "$@"