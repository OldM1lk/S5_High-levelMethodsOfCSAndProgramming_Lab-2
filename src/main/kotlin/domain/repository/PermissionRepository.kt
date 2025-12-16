package app.domain.repository

import app.domain.model.Resource
import app.domain.model.ResourceAction

interface PermissionRepository {
    fun hasPermission(user: String, resource: Resource, action: ResourceAction): Boolean
}