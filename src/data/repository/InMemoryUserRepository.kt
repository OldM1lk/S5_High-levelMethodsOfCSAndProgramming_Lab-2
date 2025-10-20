package data.repository

import domain.model.User
import domain.repository.UserRepository

class InMemoryUserRepository(
    private val users: List<User>
) : UserRepository {
    override fun findUserByLogin(login: String): User? {
        return users.find { it.login == login }
    }
}