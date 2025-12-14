package main.kotlin.data.repository.memory

import main.kotlin.domain.model.User
import main.kotlin.domain.repository.UserRepository
import main.kotlin.util.hashPassword

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