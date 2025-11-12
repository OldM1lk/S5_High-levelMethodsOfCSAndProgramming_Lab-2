package presentation

import data.repository.jdbc.PermissionRepositoryImpl
import data.repository.jdbc.ResourceRepositoryImpl
import data.repository.jdbc.UserRepositoryImpl
import domain.use_case.AuthenticateUserUseCase
import domain.use_case.CheckAccessUseCase
import util.DatabaseConnectionFactory
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    exitProcess(runApp(args))
}

fun runApp(args: Array<String>): Int {
    val parser = CommandLineParser(args)

    return when (val result = parser.parse()) {
        is CommandLineParser.ParseResult.HelpRequested -> {
            printHelp()
            1
        }

        is CommandLineParser.ParseResult.Error -> result.code

        is CommandLineParser.ParseResult.Success -> {
            val input = result.args
            val connection = DatabaseConnectionFactory.createConnection()

            try {
                connection.use { connection ->
                    val userRepository = UserRepositoryImpl(connection)
                    val resourceRepository = ResourceRepositoryImpl(connection)
                    val permissionRepository = PermissionRepositoryImpl(connection)

                    val authUseCase = AuthenticateUserUseCase(userRepository)
                    val accessUseCase = CheckAccessUseCase(permissionRepository)

                    val authCode = authUseCase(input.login, input.password)
                    if (authCode != 0) return authCode  // 2 — неверный пароль, 3 — неверный логин

                    val resource =
                        resourceRepository.findResourceByPath(input.resourcePath) ?: return 6    // ресурс не найден

                    val hasAccess = accessUseCase(input.login, resource, input.action)
                    if (!hasAccess) return 5    // нет доступа

                    if (input.volume > resource.maxVolume) return 8    // превышен объем

                    0   // успех
                }
            } catch (e: java.sql.SQLException) {
                10  // ошибка SQL-запроса
            } catch (e: Exception) {
                9   // ошибка подключения к базе данных
            }
        }
    }
}

private fun printHelp() {
    println(
        """
        Supported arguments:
        -l, --login      User login
        -p, --password   User password
        -r, --resource   Resource path
        -a, --action     Action: read, write, execute
        -v, --volume     Resource volume
        -h, --help       Show help
        """.trimIndent()
    )
}