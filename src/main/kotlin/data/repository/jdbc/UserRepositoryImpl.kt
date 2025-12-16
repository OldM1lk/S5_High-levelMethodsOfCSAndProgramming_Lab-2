package app.data.repository.jdbc

import app.domain.model.User
import app.domain.repository.UserRepository
import org.springframework.stereotype.Repository
import java.sql.Connection

@Repository
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