package data.repository

import domain.model.Resource
import domain.model.ResourceAction
import domain.repository.PermissionRepository
import java.sql.Connection

class PermissionRepository(private val connection: Connection) : PermissionRepository {
    override fun hasPermission(user: String, resource: Resource, action: ResourceAction): Boolean {
        var current: Resource? = resource

        while (current != null) {
            val stmt = connection.prepareStatement(
                "SELECT action FROM permissions WHERE user_login = ? AND resource_name = ?"
            )
            stmt.setString(1, user)
            stmt.setString(2, current.name)
            val rs = stmt.executeQuery()

            while (rs.next()) {
                val dbAction = ResourceAction.valueOf(rs.getString("action"))
                if (dbAction == action) return true
            }
            current = current.parent
        }

        return false
    }
}
