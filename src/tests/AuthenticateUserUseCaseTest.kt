package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import domain.use_case.AuthenticateUserUseCase
import data.repository.InMemoryUserRepository

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