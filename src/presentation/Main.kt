package presentation

import data.repository.InMemoryPermissionRepository
import data.repository.InMemoryResourceRepository
import data.repository.InMemoryUserRepository
import domain.use_case.AuthenticateUserUseCase
import domain.use_case.CheckAccessUseCase
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = CommandLineParser(args)
    val input = parser.parse()

    val userRepository = InMemoryUserRepository()
    val resourceRepository = InMemoryResourceRepository()
    val permissionRepository = InMemoryPermissionRepository()

    val authUseCase = AuthenticateUserUseCase(userRepository)
    val accessUseCase = CheckAccessUseCase(permissionRepository)

    val authCode = authUseCase.execute(input.login, input.password)
    if (authCode != 0) {
        exitProcess(authCode) // 3 — неверный логин, 2 — неверный пароль
    }

    val resource = resourceRepository.findResourceByPath(input.resourcePath)
        ?: exitProcess(6) // ресурс не найден

    val hasAccess = accessUseCase.execute(input.login, resource, input.action)
    if (!hasAccess) {
        exitProcess(5) // нет доступа
    }

    if (input.volume > resource.maxVolume) {
        exitProcess(8) // превышение объёма
    }

    // успешный успех
    exitProcess(0)
}