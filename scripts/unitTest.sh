#!/bin/bash

#Сборка проекта
bash build.sh

cd "$(dirname "$0")/.." || exit

java -jar lib/junit-platform-console-standalone.jar \
  --class-path app.jar \
  --scan-class-path \
  --include-package tests