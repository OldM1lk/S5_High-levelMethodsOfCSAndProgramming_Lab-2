package util

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConnectionFactory {
    fun createConnection(): Connection {
        return DriverManager.getConnection("jdbc:h2:./db/app-db", "sa", "")
    }
}
