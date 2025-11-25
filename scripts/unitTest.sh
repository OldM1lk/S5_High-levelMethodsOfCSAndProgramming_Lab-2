#!/bin/bash

#Сборка проекта
bash scripts/build.sh

cd "$(dirname "$0")/.." || exit

java -jar lib/junit-platform-console-standalone.jar \
  --class-path out/app.jar \
  --scan-class-path \
  --include-package tests