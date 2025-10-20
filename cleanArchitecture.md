# Чистая архитектура

## Распределение компонентов по слоям

    ### **Domain Layer (Слой предметной области)**

* **Сущности (Entities):**
    * `User`: Модель пользователя (логин, хэш пароля, соль).
    * `Resource`: Модель ресурса (имя, объем, иерархия).
    * `Permission`: Модель прав доступа.
    * `ResourceAction`: Enum с возможными действиями.
* **Интерфейсы репозиториев (Repository Interfaces):**
    * `UserRepository`: Интерфейс с методами `findUserByLogin(login: String)`.
    * `ResourceRepository`: Интерфейс с методами `findResourceByPath(path: String)`.
    * `PermissionRepository`: Интерфейс с методами для проверки прав.
* **Сценарии использования (Use Cases / Interactors):**
    * `AuthenticateUserUseCase`: Сценарий для аутентификации пользователя. Принимает логин и пароль, использует
      `UserRepository` и возвращает результат.
    * `CheckAccessUseCase`: Сценарий для проверки прав доступа. Принимает пользователя, ресурс и действие, использует
      `PermissionRepository`.

### **Data Layer (Слой данных)**

* **Реализации репозиториев (Repository Implementations):**
    * `InMemoryUserRepository`: Реализация `UserRepository`, которая работает с предопределенным списком `users`.
    * `InMemoryResourceRepository`: Реализация `ResourceRepository` для работы со списком `resources`.
    * `InMemoryPermissionRepository`: Реализация `PermissionRepository`.
* **Источники данных (Data Sources):** В текущем проекте это списки `users`, `resources`, `permissions`.
* **Вспомогательные классы:**
    * `PasswordHasher`: Класс, отвечающий за хеширование паролей.

### **Presentation Layer (Слой представления)**

* **Точка входа:**
    * `main()`: Основная функция приложения.
* **Компоненты UI:**
    * `CommandLineParser`: Класс, отвечающий за парсинг аргументов командной строки с помощью `kotlinx-cli`.
* **Логика представления:**
    * Код в `main`, который координирует вызовы Use Cases, обрабатывает их результаты (успех/ошибка) и выводит
      информацию пользователю, завершая процесс с нужным кодом.
