package main.kotlin.data.repository.jdbc

import main.kotlin.domain.model.Resource
import main.kotlin.domain.model.ResourceAction
import main.kotlin.domain.repository.PermissionRepository
import java.sql.Connection

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
