package app.data.repository.memory

import app.domain.model.User
import app.domain.repository.UserRepository
import app.util.hashPassword

class InMemoryUserRepository() : UserRepository {
    private val users = listOf(
        User(
            "alice",
            "salt1",
            hashPassword("qwerty", "salt1")
        ),
        User(
            "bob",
            "salt2",
            hashPassword("12345", "salt2")
        )
    )

    override fun findUserByLogin(login: String): User? {
        return users.find { it.login == login }
    }
}