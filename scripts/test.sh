#!/bin/bash

APP="./scripts/run.sh"
TOTAL=0
PASSED=0

# Тест: успешное выполнение
$APP -l alice -p qwerty -r B.C -a read -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 0 ]; then
  echo "Test 1: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 1: FAIL (got $CODE)"
fi

# Тест: запрошена справка
$APP --help
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 1 ]; then
  echo "Test 2: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 2: FAIL (got $CODE)"
fi

# Тест: неверный пароль
$APP -l alice -p wrong -r B.C -a read -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 2 ]; then
  echo "Test 3: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 3: FAIL (got $CODE)"
fi

# Тест: неверный логин
$APP -l unknown -p qwerty -r B.C -a read -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 3 ]; then
  echo "Test 4: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 4: FAIL (got $CODE)"
fi

# Тест: неизвестное действие над ресурсом
$APP -l alice -p qwerty -r B.C -a delete -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 4 ]; then
  echo "Test 5: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 5: FAIL (got $CODE)"
fi

# Тест: нет доступа
$APP -l bob -p 12345 -r B -a write -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 5 ]; then
  echo "Test 6: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 6: FAIL (got $CODE)"
fi

# Тест: несуществующий ресурс
$APP -l alice -p qwerty -r Z.Z -a read -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 6 ]; then
  echo "Test 7: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 7: FAIL (got $CODE)"
fi

# Тест: некорректный формат ресурса
$APP -l alice -p qwerty -r "A.B$.C" -a read -v 10
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 7 ]; then
  echo "Test 8: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 8: FAIL (got $CODE)"
fi

# Тест: превышение максимального объема
$APP -l alice -p qwerty -r B.C -a read -v 999
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 8 ]; then
  echo "Test 9: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 9: FAIL (got $CODE)"
fi

echo
echo "Result: $PASSED/$TOTAL tests passed"