package data.repository.memory

import domain.model.User
import domain.repository.UserRepository
import util.hashPassword

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