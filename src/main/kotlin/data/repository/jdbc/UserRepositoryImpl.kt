package data.repository.jdbc

import domain.model.User
import domain.repository.UserRepository
import java.sql.Connection

class UserRepositoryImpl(private val connection: Connection) : UserRepository {
    override fun findUserByLogin(login: String): User? {
        val sql = "SELECT * FROM users WHERE login = ?"

        return connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, login)
            stmt.executeQuery().use { rs ->
                if (rs.next()) {
                    User(
                        login = rs.getString("login"),
                        salt = rs.getString("salt"),
                        passwordHash = rs.getString("password_hash")
                    )
                } else null
            }
        }
    }
}