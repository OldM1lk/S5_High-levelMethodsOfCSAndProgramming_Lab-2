package app.util

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConnection {
    private const val DEFAULT_URL = "jdbc:h2:./data_source/app-db"
    private const val DEFAULT_USER = "sa"
    private const val DEFAULT_PASSWORD = ""

    fun createConnection(): Connection {
        return DriverManager.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD)
    }
}