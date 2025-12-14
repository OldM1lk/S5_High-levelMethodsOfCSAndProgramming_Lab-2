package main.kotlin.data.repository.memory

import main.kotlin.domain.model.Permission
import main.kotlin.domain.model.Resource
import main.kotlin.domain.model.ResourceAction
import main.kotlin.domain.repository.PermissionRepository

class InMemoryPermissionRepository() : PermissionRepository {
    private val permissions = listOf(
        Permission(
            "alice",
            "B",
            setOf(ResourceAction.READ, ResourceAction.WRITE, ResourceAction.EXECUTE)
        ),
        Permission(
            "bob",
            "X",
            setOf(ResourceAction.READ)
        )
    )

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