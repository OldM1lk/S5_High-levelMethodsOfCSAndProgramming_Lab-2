package presentation

import domain.repository.ResourceRepository
import domain.use_case.AuthenticateUserUseCase
import domain.use_case.CheckAccessUseCase
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Component
class CliRunner(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val checkAccessUseCase: CheckAccessUseCase,
    private val resourceRepository: ResourceRepository
) : CommandLineRunner {
    override fun run(args: Array<String>) {
        val exitCode = runApp(args, authenticateUserUseCase, checkAccessUseCase, resourceRepository)
        exitProcess(exitCode)
    }
}

fun runApp(
    args: Array<String>,
    authUseCase: AuthenticateUserUseCase,
    accessUseCase: CheckAccessUseCase,
    resourceRepository: ResourceRepository
): Int {
    val parser = CommandLineParser(args)

    return when (val result = parser.parse()) {
        is CommandLineParser.ParseResult.HelpRequested -> {
            printHelp()
            1
        }

        is CommandLineParser.ParseResult.Error -> result.code

        is CommandLineParser.ParseResult.Success -> {
            val input = result.args

            val authCode = authUseCase(input.login, input.password)
            if (authCode != 0) return authCode  // 2 — неверный пароль, 3 — неверный логин

            val resource =
                resourceRepository.findResourceByPath(input.resourcePath) ?: return 6    // ресурс не найден

            val hasAccess = accessUseCase(input.login, resource, input.action)
            if (!hasAccess) return 5    // нет доступа

            if (input.volume > resource.maxVolume) return 8    // превышен объем

            0   // успех
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