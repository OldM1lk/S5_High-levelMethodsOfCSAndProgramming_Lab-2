#!/bin/bash
set -e

# Переход в корень проекта относительно папки scripts
cd "$(dirname "$0")/.."

# Чистка старых сборок
rm -rf build
mkdir -p build

# Сборка fat-jar
kotlinc $(find src -name "*.kt") \
  -include-runtime \
  -cp "lib/h2-2.4.240.jar:lib/kotlinx-cli-jvm-0.3.6.jar:lib/junit-platform-console-standalone.jar" \
  -d build/app-fat.jar

echo "Fat-jar собран: build/app-fat.jar"
