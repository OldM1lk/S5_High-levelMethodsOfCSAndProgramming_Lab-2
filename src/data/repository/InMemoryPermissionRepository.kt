package data.repository

import domain.model.Permission
import domain.model.Resource
import domain.model.ResourceAction
import domain.repository.PermissionRepository

class InMemoryPermissionRepository(
    private val permissions: List<Permission>
) : PermissionRepository {
    override fun hasPermission(
        user: String,
        resource: Resource,
        action: ResourceAction
    ): Boolean {
        var current: Resource? = resource
        while (current != null) {
            val perm = permissions.find { it.userLogin == user && it.resourceName == current.name }
            if (perm != null && action in perm.actions) return true
            current = current.parent
        }
        return false
    }
}