package app.presentation

import app.domain.model.ResourceAction
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

class CommandLineParser(private val argv: Array<String>) {
    data class ParsedArgs(
        val login: String,
        val password: String,
        val resourcePath: String,
        val action: ResourceAction,
        val volume: Int
    )

    sealed class ParseResult {
        data class Success(val args: ParsedArgs) : ParseResult()
        data class Error(val code: Int) : ParseResult()
        object HelpRequested : ParseResult()
    }

    fun parse(): ParseResult {
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
            return ParseResult.HelpRequested
        }

        return try {
            parser.parse(argv)

            val resourceAction = action.toResourceAction() ?: return ParseResult.Error(4)

            if (!isValidResourcePath(resourcePath))
                return ParseResult.Error(7)

            ParseResult.Success(ParsedArgs(login, password, resourcePath, resourceAction, volume))
        } catch (_: Exception) {
            ParseResult.Error(7)
        }
    }

    private fun String.toResourceAction(): ResourceAction? =
        when (lowercase()) {
            "read" -> ResourceAction.READ
            "write" -> ResourceAction.WRITE
            "execute" -> ResourceAction.EXECUTE
            else -> null
        }

    private fun isValidResourcePath(path: String): Boolean {
        val parts = path.split(".")
        return parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }
    }
}