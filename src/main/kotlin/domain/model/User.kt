package domain.model

data class User(
    val login: String,
    val salt: String,
    val passwordHash: String
)
