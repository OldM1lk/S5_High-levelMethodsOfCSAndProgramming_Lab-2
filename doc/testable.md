# Анализ тестопригодности

---

## Что нужно протестировать

- `AuthenticateUserUseCase` - Проверка кода возврата: 0 – успех, 2 – неверный пароль, 3 – неверный логин
- `CheckAccessUseCase` - Корректность делегирования вызова `PermissionRepository.hasPermission()`
- `ResourceNavigator` - Построение полного пути, поиск ресурса по пути, определение дочерних элементов
- `HashUtil.hashPassword()` - Стабильность хеша при одинаковом входе, различие при разном
- `InMemoryPermissionRepository` - Проверка наследования прав через иерархию `Resource.parent`
- `InMemoryResourceRepository` - Корректное построение и поиск по пути `A.B.C`
- `InMemoryUserRepository` - Поиск пользователя по логину
- `CommandLineParser` - Сейчас использует `exitProcess()` для ошибок и помощи, тем самым мешая тестированию, нужно
  вынести коды ошибок в `sealed class ParseResult` (успех / ошибка / help). `exitProcess` вызывать только в `Main`

## Минимальный рефакторинг

1. **CommandLineParser** - Вместо `exitProcess()` возвращать объект результата:

      ```kotlin
      sealed class ParseResult {
          data class Success(val args: ParsedArgs) : ParseResult()
          data class Error(val code: Int) : ParseResult()
          object HelpRequested : ParseResult()
      }
      ```

   Тогда `Main.kt` сможет обработать результат и вызвать `exitProcess()` сам.

2. **Main.kt** - Обернуть основную логику в функцию `runApp(args: Array<String>): Int`, возвращающую код завершения.
   Это позволит тестировать сценарии без реального выхода из программы.

## Примеры проверяемых сценариев

| Название теста                                                         | Краткое описание                                           |
|------------------------------------------------------------------------|------------------------------------------------------------|
| `AuthenticateUserUseCaseTest.shouldReturnSuccessForCorrectPassword()`  | Проверяет корректный логин/пароль                          |
| `AuthenticateUserUseCaseTest.shouldReturnErrorForWrongPassword()`      | Проверяет ошибку 2                                         |
| `InMemoryPermissionRepositoryTest.shouldInheritPermissionFromParent()` | Проверяет, что права наследуются вверх по цепочке ресурсов |
| `ResourceNavigatorTest.shouldBuildFullPathCorrectly()`                 | Проверяет корректность конкатенации `A.B.C`                |
| `ResourceNavigatorTest.shouldFindResourceByPath()`                     | Проверяет корректность навигации                           |
| `HashUtilTest.shouldGenerateSameHashForSameInput()`                    | Проверяет детерминированность хеша                         |
| `CommandLineParserTest.shouldReturnHelpResultForHelpFlag()`            | После рефакторинга, проверка обработки `--help`            |

---

## Классы, которые пока не тестируются

| Класс                                | Причина                                                        |
|--------------------------------------|----------------------------------------------------------------|
| `Main.kt`                            | Содержит точки выхода (`exitProcess`), интеграционный сценарий |
| `CommandLineParser` (в текущем виде) | Использует `exitProcess`, требуется рефакторинг                |

## Сколько тестов выполнено / успешно прошло

Тесты пока **не реализованы**, анализ проведён для подготовки к следующему заданию.

| Метрика                           | Значение                     |
|-----------------------------------|------------------------------|
| Количество запланированных тестов | 7                            |
| Выполнено / прошло успешно        | 0 / 0                        |
| Покрытие логики (оценочно)        | ~70% после добавления тестов |
