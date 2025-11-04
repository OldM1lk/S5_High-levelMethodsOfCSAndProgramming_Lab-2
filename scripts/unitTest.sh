#!/bin/bash

#Сборка проекта
bash scripts/build.sh

java -jar lib/junit-platform-console-standalone.jar \
  --class-path app.jar \
  --scan-class-path \
  --include-package tests