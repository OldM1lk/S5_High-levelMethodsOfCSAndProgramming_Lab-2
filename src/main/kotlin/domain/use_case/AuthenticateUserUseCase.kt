package domain.use_case

import domain.repository.UserRepository
import main.kotlin.util.hashPassword
import org.springframework.stereotype.Service

@Service
class AuthenticateUserUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(login: String, password: String): Int {
        val user = repository.findUserByLogin(login) ?: return 3 // неверный логин
        val inputHash = hashPassword(password, user.salt)
        return if (inputHash == user.passwordHash) 0 else 2 // 0 = успех, 2 = неверный пароль
    }
}
