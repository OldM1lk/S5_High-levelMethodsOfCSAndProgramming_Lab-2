package test.kotlin.tests

import main.kotlin.data.repository.memory.InMemoryUserRepository
import main.kotlin.domain.use_case.AuthenticateUserUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthenticateUserUseCaseTest {
    @Test
    fun shouldReturnSuccessForCorrectPassword() {
        val repository = InMemoryUserRepository()
        val useCase = AuthenticateUserUseCase(repository)

        val result = useCase("alice", "qwerty")

        assertEquals(0, result)
    }

    @Test
    fun shouldReturnErrorForWrongPassword() {
        val repository = InMemoryUserRepository()
        val useCase = AuthenticateUserUseCase(repository)

        val result = useCase("alice", "wrong")

        assertEquals(2, result)
    }
}