import java.security.MessageDigest

fun hashPassword(password: String, salt: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest((password + salt).toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }
}