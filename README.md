# Лабораторная работа №2

## Авторы

- Нефедов Егор
- Семенов Максим

### Сборка

Собираем проект с помощью `build.sh` (необходимо наличие Git Bash / Linux / WSL):

```bash
bash scripts/build.sh
```

После выполнения появится файл `out/app.jar`.

### Запуск

Запуск программы с аргументами командной строки:

```bash
bash scripts/run.sh -l <login> -p <password> -r <resource> -a <action> -v <volume>
```

Пример:

```bash
bash run.sh -l alice -p qwerty -r B.C -a read -v 10
```

### Тестирование

Для автоматической проверки кодов возврата:

```bash
bash scripts/test.sh
```

Для unit-тестирования:

```bash
bash scripts/unitTest.sh
```