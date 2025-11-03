#!/bin/bash
set -e

# Переход в корень проекта относительно папки scripts
cd "$(dirname "$0")/.."

# Чистка старых сборок
rm -rf out
mkdir -p out

# Компиляция
kotlinc src -cp lib/kotlinx-cli-jvm-0.3.6.jar -include-runtime -d out/app.jar

echo "Сборка завершена: out/app.jar"