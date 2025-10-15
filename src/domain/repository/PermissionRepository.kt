package domain.repository

import domain.model.Resource
import domain.model.ResourceAction

interface PermissionRepository {
    fun hasPermission(user: String, resource: Resource, action: ResourceAction)
}