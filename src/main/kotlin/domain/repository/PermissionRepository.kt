package main.kotlin.domain.repository

import main.kotlin.domain.model.Resource
import main.kotlin.domain.model.ResourceAction

interface PermissionRepository {
    fun hasPermission(user: String, resource: Resource, action: ResourceAction): Boolean
}