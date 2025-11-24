#!/bin/bash
set -e

# Переход в корень проекта относительно папки scripts
cd "$(dirname "$0")/.."

# Чистка старых сборок
rm -rf out
mkdir -p out

# Определяем разделитель classpath
if [[ "$OS" == "Windows_NT" ]]; then
    SEP=";"
else
    SEP=":"
fi

# Компиляция
CP="lib/junit-platform-console-standalone.jar${SEP}lib/kotlinx-cli-jvm-0.3.6.jar${SEP}lib/h2-2.4.240.jar"

kotlinc $(find src -name "*.kt") -cp "$CP" -include-runtime -d "out/app.jar"
echo "Сборка завершена: out/app.jar"