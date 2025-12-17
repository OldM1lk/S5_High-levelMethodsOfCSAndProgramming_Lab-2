#!/bin/bash
set -e

# Переход в корень проекта относительно папки scripts
cd "$(dirname "$0")/.."

# Путь к jar-файлу H2
H2_JAR="lib/h2-2.4.240.jar"

# Создаём папку для базы
mkdir -p "data_source"

# Инициализация базы: применяем schema.sql и fill.sql
echo "Инициализация базы данных..."
java -cp "$H2_JAR" org.h2.tools.RunScript \
  -url "jdbc:h2:./data_source/app-db" \
  -user sa \
  -script "scripts/schema.sql"

java -cp "$H2_JAR" org.h2.tools.RunScript \
  -url "jdbc:h2:./data_source/app-db" \
  -user sa \
  -script "scripts/fill.sql"

echo "База данных успешно инициализирована: data_source/app-db.mv.db"
