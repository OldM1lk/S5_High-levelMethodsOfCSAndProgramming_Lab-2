package config

import data.repository.jdbc.PermissionRepositoryImpl
import data.repository.jdbc.ResourceRepositoryImpl
import data.repository.jdbc.UserRepositoryImpl
import domain.repository.PermissionRepository
import domain.repository.ResourceRepository
import domain.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import util.DatabaseConnection
import java.sql.Connection

@Configuration
class DatabaseConfig {

    @Bean
    fun connection(): Connection =
        DatabaseConnection.createConnection()

    @Bean
    fun userRepository(connection: Connection): UserRepository =
        UserRepositoryImpl(connection)

    @Bean
    fun resourceRepository(connection: Connection): ResourceRepository =
        ResourceRepositoryImpl(connection)

    @Bean
    fun permissionRepository(connection: Connection): PermissionRepository =
        PermissionRepositoryImpl(connection)
}
