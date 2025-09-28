import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

fun main(args: Array<String>) {
    val parser = ArgParser("app")

    if (args.contains("-h") || args.contains("--help")) {
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
        kotlin.system.exitProcess(1)
    }

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

    try {
        parser.parse(args)
    } catch (e: Exception) {
        println("Неверный формат запуска. Используйте -h для справки.")
        kotlin.system.exitProcess(7) // неверный формат
    }

    val authCode = authenticate(login, password)
    if (authCode != 0) kotlin.system.exitProcess(authCode) // неверный логин или пароль

    val pathParts = resourcePath.split(".")
    if (!pathParts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) {
        kotlin.system.exitProcess(7) // неверный формат
    }

    val resource = findResourceByPath(resourcePath)
        ?: kotlin.system.exitProcess(6) // несуществующий ресурс

    val resourceAction = when (action.lowercase()) {
        "read" -> ResourceAction.READ
        "write" -> ResourceAction.WRITE
        "execute" -> ResourceAction.EXECUTE
        else -> kotlin.system.exitProcess(4) // неизвестное действие над ресурсом
    }

    // нет доступа
    if (!hasPermission(login, resource, resourceAction)) kotlin.system.exitProcess(5)

    // превышение максимального объема
    if (volume > resource.maxVolume) kotlin.system.exitProcess(8)

    // успешное выполнение
    kotlin.system.exitProcess(0)
}
