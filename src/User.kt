data class User(val login: String, val salt: String, val passwordHash: String)

val users = listOf(
    User("alice", "salt1", hashPassword("qwerty", "salt1")),
    User("bob", "salt2", hashPassword("12345", "salt2"))
)