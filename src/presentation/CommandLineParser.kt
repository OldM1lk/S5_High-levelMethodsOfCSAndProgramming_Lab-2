package presentation

import domain.model.ResourceAction
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import kotlin.system.exitProcess

class CommandLineParser(private val argv: Array<String>) {
    data class ParsedArgs(
        val login: String,
        val password: String,
        val resourcePath: String,
        val action: ResourceAction,
        val volume: Int
    )

    fun parse(): ParsedArgs {
        val parser = ArgParser("app")

        val login by parser.option(
            ArgType.String,
            shortName = "l",
            fullName = "login",
            description = "User login"
        ).required()

        val password by parser.option(
            ArgType.String,
            shortName = "p",
            fullName = "password",
            description = "User password"
        ).required()

        val resourcePath by parser.option(
            ArgType.String,
            shortName = "r",
            fullName = "resource",
            description = "Resource path"
        ).required()

        val action by parser.option(
            ArgType.String,
            shortName = "a",
            fullName = "action",
            description = "Action: read, write, execute"
        ).required()

        val volume by parser.option(
            ArgType.Int,
            shortName = "v",
            fullName = "volume",
            description = "Requested volume"
        ).required()

        if (argv.contains("-h") || argv.contains("--help")) {
            printHelp()
            exitProcess(1) // справка
        }

        try {
            parser.parse(argv)
        } catch (e: Exception) {
            exitProcess(7) // неверный формат
        }

        val resourceAction = when (action.lowercase()) {
            "read" -> ResourceAction.READ
            "write" -> ResourceAction.WRITE
            "execute" -> ResourceAction.EXECUTE
            else -> {
                exitProcess(4) // неизвестное действие
            }
        }

        val parts = resourcePath.split(".")
        if (!parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) {
            exitProcess(7) // неверный формат
        }

        return ParsedArgs(
            login = login,
            password = password,
            resourcePath = resourcePath,
            action = resourceAction,
            volume = volume
        )
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
}