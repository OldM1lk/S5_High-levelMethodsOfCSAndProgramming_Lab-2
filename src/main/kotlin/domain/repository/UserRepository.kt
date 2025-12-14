package main.kotlin.domain.repository

import main.kotlin.domain.model.User

interface UserRepository {
    fun findUserByLogin(login: String): User?
}