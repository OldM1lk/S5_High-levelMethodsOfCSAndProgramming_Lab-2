package app.presentation

import app.domain.repository.ResourceRepository
import app.domain.use_case.AuthenticateUserUseCase
import app.domain.use_case.CheckAccessUseCase
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class CliRunner(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val checkAccessUseCase: CheckAccessUseCase,
    private val resourceRepository: ResourceRepository
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(CliRunner::class.java)

    override fun run(args: Array<String>) {
        log.info("Application started with args: {}", args.joinToString(" "))
        val exitCode = runApp(args, authenticateUserUseCase, checkAccessUseCase, resourceRepository)
        log.info("Application finished with exit code {}", exitCode)
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