package domain.repository

interface UserRepository {
    fun findUserByLogin(login: String)
}