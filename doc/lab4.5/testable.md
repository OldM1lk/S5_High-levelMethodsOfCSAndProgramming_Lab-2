# Анализ тестопригодности

---

## Что нужно протестировать

- `AuthenticateUserUseCase` - Проверка кода возврата: 0 – успех, 2 – неверный пароль, 3 – неверный логин
- `CheckAccessUseCase` - Корректность делегирования вызова `PermissionRepository.hasPermission()`
- `ResourceNavigator` - Построение полного пути, поиск ресурса по пути, определение дочерних элементов
- `HashUtil.hashPassword()` - Стабильность хеша при одинаковом входе, различие при разном
- `InMemoryResourceRepository` - Корректное построение и поиск по пути `A.B.C`

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

| Название теста                                                                     | Краткое описание                                                                      |
|------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `AuthenticateUserUseCaseTest.shouldReturnSuccessForCorrectPassword()`              | Проверяет успешную аутентификацию при правильных данных                               |
| `AuthenticateUserUseCaseTest.shouldReturnErrorForWrongPassword()`                  | Проверяет возврат кода ошибки при неверном пароле                                     |
| `InMemoryPermissionRepositoryTest.shouldInheritPermissionFromParent()`             | Проверяет, что доступ наследуется от родительского ресурса                            |
| `InMemoryPermissionRepositoryTest.shouldReturnFalseForUserWithoutAnyPermissions()` | Проверяет, что пользователь без прав не получает доступ                               |
| `ResourceNavigatorTest.shouldBuildFullPathCorrectly()`                             | Проверяет корректное построение полного пути ресурса (A.B.C)                          |
| `ResourceNavigatorTest.shouldFindResourceByPath()`                                 | Проверяет успешный поиск ресурса по корректному пути                                  |
| `ResourceNavigatorTest.shouldReturnNullForNonexistentPath()`                       | Проверяет возврат `null` при неверном или несуществующем пути                         |
| `HashUtilTest.shouldGenerateSameHashForSameInput()`                                | Проверяет, что одинаковый пароль и соль дают одинаковый хэш                           |
| `HashUtilTest.shouldGenerateDifferentHashForDifferentSalt()`                       | Проверяет, что разная соль даёт разные хэши для одного пароля                         |
| `CheckAccessUseCase.shouldDelegateCallToPermissionRepositoryAndReturnTrue()`       | Проверяет, что use case корректно возвращает true, если репозиторий разрешает доступ  |
| `CheckAccessUseCase.shouldDelegateCallToPermissionRepositoryAndReturnFalse()`      | Проверяет, что use case корректно возвращает false, если репозиторий запрещает доступ |

---

## Классы, которые пока не тестируются

| Класс                                | Причина                                                        |
|--------------------------------------|----------------------------------------------------------------|
| `Main.kt`                            | Содержит точки выхода (`exitProcess`), интеграционный сценарий |
| `CommandLineParser` (в текущем виде) | Использует `exitProcess`, требуется рефакторинг                |

## Сколько тестов выполнено / успешно прошло

Все тесты успешно прошли

| Метрика                           | Значение                     |
|-----------------------------------|------------------------------|
| Количество запланированных тестов | 11                           |
| Выполнено / прошло успешно        | 11 / 11                      |
| Покрытие логики (оценочно)        | ~80% после добавления тестов |
