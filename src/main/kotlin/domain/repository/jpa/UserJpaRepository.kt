package app.domain.repository.jpa

import app.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String): User?
}