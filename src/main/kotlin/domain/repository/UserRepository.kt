package app.domain.repository

import app.domain.model.User

interface UserRepository {
    fun findUserByLogin(login: String): User?
}