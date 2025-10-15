package domain.use_case

import domain.repository.UserRepository
import hashPassword

class AuthenticateUserUseCase(
    private val repository: UserRepository,
) {
    fun execute(login: String, password: String): Int {
        val user = repository.findUserByLogin(login) ?: return 3 // неверный логин
        val inputHash = hashPassword(password, user.salt)
        return if (inputHash == user.passwordHash) 0 else 2 // 0 = успех, 2 = неверный пароль
    }
}
