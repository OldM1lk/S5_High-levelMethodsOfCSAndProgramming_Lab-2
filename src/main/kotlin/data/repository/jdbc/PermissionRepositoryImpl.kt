package app.data.repository.jdbc

import app.domain.model.Resource
import app.domain.model.ResourceAction
import app.domain.repository.PermissionRepository
import org.springframework.stereotype.Repository
import java.sql.Connection

@Repository
class PermissionRepositoryImpl(private val connection: Connection) : PermissionRepository {
    override fun hasPermission(user: String, resource: Resource, action: ResourceAction): Boolean {
        val sql = "SELECT action FROM permissions WHERE user_login = ? AND resource_name = ?"
        var current: Resource? = resource

        while (current != null) {
            val hasPermission = connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, user)
                stmt.setString(2, current.name)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val dbAction = ResourceAction.valueOf(rs.getString("action"))
                        if (dbAction == action) return true
                    }
                    false
                }
            }

            if (hasPermission) return true
            current = current.parent
        }

        return false
    }
}
