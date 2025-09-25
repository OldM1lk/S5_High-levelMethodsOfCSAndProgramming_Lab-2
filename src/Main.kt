import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

fun main(args: Array<String>) {
    val parser = ArgParser("app")

    val login by parser.option(
        ArgType.String,
        shortName = "l",
        description = "User login"
    ).required()

    val password by parser.option(
        ArgType.String,
        shortName = "p",
        description = "User password"
    ).required()

    try {
        parser.parse(args)
    } catch (e: Exception) {
        println("Неверный формат запуска. Используйте -h для справки.")
        kotlin.system.exitProcess(7) // неверный формат
    }

    val result = authenticate(login, password)

    when (result) {
        0 -> kotlin.system.exitProcess(0)
        2 -> kotlin.system.exitProcess(2)
        3 -> kotlin.system.exitProcess(3)
    }
}
