package data.repository

import domain.model.User
import domain.repository.UserRepository
import java.sql.Connection

class UserRepositoryImpl(private val connection: Connection) : UserRepository {
    override fun findUserByLogin(login: String): User? {
        val stmt = connection.prepareStatement("SELECT * FROM users WHERE login = ?")
        stmt.setString(1, login)
        val rs = stmt.executeQuery()
        return if (rs.next()) {
            User(
                login = rs.getString("login"),
                salt = rs.getString("salt"),
                passwordHash = rs.getString("password_hash")
            )
        } else null
    }
}