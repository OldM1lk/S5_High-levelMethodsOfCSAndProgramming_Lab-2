package domain.repository

import domain.model.User

interface UserRepository {
    fun findUserByLogin(login: String) : User?
}