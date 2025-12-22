package app.data.repository.jpa

import app.domain.model.User
import app.domain.repository.UserRepository
import app.domain.repository.jpa.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserJpaRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findUserByLogin(login: String): User? {
        return userJpaRepository.findByLogin(login)?.let {
            User(
                login = it.login,
                salt = it.salt,
                passwordHash = it.passwordHash
            )
        }
    }
}
