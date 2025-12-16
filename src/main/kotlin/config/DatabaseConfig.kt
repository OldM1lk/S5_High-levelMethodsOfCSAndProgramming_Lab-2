package app.config

import app.util.DatabaseConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.Connection

@Configuration
open class DatabaseConfig {

    @Bean
    open fun connection(): Connection =
        DatabaseConnection.createConnection()
}
