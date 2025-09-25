#!/bin/bash

APP="./run.sh"
TOTAL=0
PASSED=0

# Тест: успешная аутентификация
$APP -l alice -p qwerty
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 0 ]; then
  echo "Test 1: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 1: FAIL (got $CODE)"
fi

# Тест: неверный пароль
$APP -l alice -p wrong
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 2 ]; then
  echo "Test 2: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 2: FAIL (got $CODE)"
fi

# Тест: неверный логин
$APP -l unknown -p qwerty
CODE=$?
TOTAL=$((TOTAL+1))
if [ $CODE -eq 3 ]; then
  echo "Test 3: OK"
  PASSED=$((PASSED+1))
else
  echo "Test 3: FAIL (got $CODE)"
fi

echo
echo "Result: $PASSED/$TOTAL tests passed"