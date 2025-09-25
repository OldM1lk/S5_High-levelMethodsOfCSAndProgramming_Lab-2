#!/bin/bash
set -e

# Чистка старых сборок
rm -rf out
mkdir -p out

# Компиляция
kotlinc src -cp lib/kotlinx-cli-jvm-0.3.6.jar -include-runtime -d out/app.jar

echo "Сборка завершена: out/app.jar"